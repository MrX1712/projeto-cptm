function toggleEdit(id) {
    const input = document.getElementById(id);
    if (input.hasAttribute('readonly')) {
        input.removeAttribute('readonly');
        input.focus();
    } else {
        input.setAttribute('readonly', true);
    }
}

function showLogoutPopup() {
    document.getElementById('logoutPopup').classList.add('active');
}

function showSuccessPopup() {
    document.getElementById('successPopup').classList.add('active');
}

function hidePopup(popupId) {
    document.getElementById(popupId).classList.remove('active');
}

function confirmLogout() {
    hidePopup('logoutPopup');
    location.href = '/cptm+/logout';
}

function handleFormSubmit() {
    setTimeout(function () {
        const mensagemElement = document.querySelector('.alert-success');
        if (mensagemElement && mensagemElement.textContent.trim()) {
            showSuccessPopup();
            mensagemElement.style.display = 'none';
        }
    }, 100);
    return true;
}

document.querySelectorAll('.popup-overlay').forEach(overlay => {
    overlay.addEventListener('click', function (e) {
        if (e.target === this) {
            this.classList.remove('active');
        }
    });
});

document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') {
        document.querySelectorAll('.popup-overlay.active').forEach(popup => {
            popup.classList.remove('active');
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const mensagemElement = document.querySelector('.alert-success');
    if (mensagemElement && mensagemElement.textContent.trim()) {
        showSuccessPopup();
        mensagemElement.style.display = 'none';
    }
});
