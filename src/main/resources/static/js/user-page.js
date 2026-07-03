const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

async function loadUserData() {
    try {
        const response = await fetch('/api/user', {
            credentials: 'same-origin'
        });
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.info || 'Failed to load user data')
        }
        const user = await response.json();
        renderUser(user);
    } catch (error) {
        console.error(error);
        document.getElementById('userTableBody').innerHTML = `
            <tr><td colspan="5" class="text-danger">${error.message}</td></tr>
        `;
    }
}

function renderUser(user) {
    const tbody = document.getElementById('userTableBody');
    tbody.innerHTML = `
        <tr>
            <td>${user.id}</td>
            <td>${escapeHtml(user.username)}</td>
            <td>${escapeHtml(user.lastname)}</td>
            <td>${user.age}</td>
            <td>${user.roleNames ? user.roleNames.join(', ') : ''}</td>
        </tr>
    `;
}

function escapeHtml(text) {
    const map = { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#039;' };
    return String(text).replace(/[&<>"']/g, m => map[m]);
}

document.addEventListener('DOMContentLoaded', () => {
    const logoutBtn =
        document.getElementById('logoutBtn');
    if(logoutBtn) {
        logoutBtn.addEventListener('click', async () => {
            try {
                const response = await fetch('/logout', {
                    method: 'POST',
                    credentials: 'same-origin',
                    headers: {[csrfHeader]: csrfToken}
                });
                if(!response.ok) {
                    window.location.href = '/login';
                }
            } catch (error) {
                console.error('Logout error', error);
            }
        });
    }
    loadUserData();
});