// Animate elements on page load
document.addEventListener('DOMContentLoaded', function () {
    // Add fade-in animation to all cards
    const cards = document.querySelectorAll('.card, .stat-card');
    cards.forEach((card, index) => {
        card.style.opacity = '0';
        setTimeout(() => {
            card.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
            card.style.opacity = '1';
        }, index * 100);
    });

    // Animate stat values counting up
    const statValues = document.querySelectorAll('.stat-value');
    statValues.forEach(stat => {
        const target = parseInt(stat.textContent);
        if (!isNaN(target)) {
            animateValue(stat, 0, target, 1500);
        }
    });

    // Form validation enhancement
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function (e) {
            const inputs = form.querySelectorAll('input[required], select[required]');
            let isValid = true;

            inputs.forEach(input => {
                if (!input.value.trim()) {
                    isValid = false;
                    input.classList.add('border-red-500');
                    input.addEventListener('input', function () {
                        this.classList.remove('border-red-500');
                    }, { once: true });
                }
            });

            if (!isValid) {
                e.preventDefault();
                showNotification('Por favor complete todos los campos requeridos', 'error');
            }
        });
    });

    // Auto-hide alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.transition = 'opacity 0.5s ease';
            alert.style.opacity = '0';
            setTimeout(() => alert.remove(), 500);
        }, 5000);
    });
});

// Animate number counting
function animateValue(element, start, end, duration) {
    const startTime = performance.now();

    function update(currentTime) {
        const elapsed = currentTime - startTime;
        const progress = Math.min(elapsed / duration, 1);

        const value = Math.floor(progress * (end - start) + start);
        element.textContent = value;

        if (progress < 1) {
            requestAnimationFrame(update);
        }
    }

    requestAnimationFrame(update);
}

// Show notification
function showNotification(message, type = 'success') {
    const notification = document.createElement('div');
    notification.className = `alert alert-${type} fixed top-4 right-4 z-50`;
    notification.style.minWidth = '300px';
    notification.innerHTML = `
        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
        </svg>
        <span>${message}</span>
    `;

    document.body.appendChild(notification);

    setTimeout(() => {
        notification.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
        notification.style.opacity = '0';
        notification.style.transform = 'translateX(100%)';
        setTimeout(() => notification.remove(), 500);
    }, 3000);
}

// Confirm delete actions
function confirmDelete(message = '¿Está seguro de eliminar este elemento?') {
    return confirm(message);
}

// Format currency
function formatCurrency(value) {
    return new Intl.NumberFormat('es-AR', {
        style: 'currency',
        currency: 'ARS'
    }).format(value);
}

// Initialize tooltips (if using)
function initTooltips() {
    const tooltips = document.querySelectorAll('[data-tooltip]');
    tooltips.forEach(element => {
        element.addEventListener('mouseenter', function () {
            const tooltip = document.createElement('div');
            tooltip.className = 'absolute bg-gray-900 text-white text-sm px-2 py-1 rounded';
            tooltip.textContent = this.getAttribute('data-tooltip');
            this.appendChild(tooltip);
        });

        element.addEventListener('mouseleave', function () {
            const tooltip = this.querySelector('.absolute');
            if (tooltip) tooltip.remove();
        });
    });
}
