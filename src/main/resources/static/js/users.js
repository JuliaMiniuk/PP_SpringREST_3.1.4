const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');


let deleteModal, updateModal, createModal;


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
    loadUsers();
    loadRolesForForm();
    loadRolesForUpdateForm();


    deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    updateModal = new bootstrap.Modal(document.getElementById('updateModal'));


    document.getElementById('confirmDeleteBtn').addEventListener('click', confirmDelete);


    document.getElementById('updateBtn').addEventListener('click', updateUser);

    document.getElementById('createBtn').addEventListener('click', saveUser);
});


async function loadUsers() {
    try {
        const response = await fetch('/api/admin/users', {
            credentials: 'same-origin'
        });
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.info || 'Failed to load user data')
        }
        const users = await response.json();
        renderTable(users);
    } catch (error) {
        console.error(error);
        alert(error.message || 'Error loading users');
    }
}

function renderTable(users) {
    const tbody = document.getElementById('usersTableBody');
    tbody.innerHTML = '';
    users.forEach(user => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${user.id}</td>
            <td>${escapeHtml(user.username)}</td>
            <td>${escapeHtml(user.lastname)}</td>
            <td>${user.age}</td>
            <td>${user.roleNames ? user.roleNames.join(', ') : ''}</td>
            <td>
                <button class="btn btn-primary btn-sm" onclick="openEditModal(${user.id})">Update</button>
                <button class="btn btn-danger btn-sm" onclick="openDeleteModal(${user.id})">Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function escapeHtml(text) {
    if (!text) return '';
    const map = {'&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#039;'};
    return text.replace(/[&<>"']/g, m => map[m]);
}


async function loadRolesForForm() {
    try {
        const response = await fetch('/api/roles', {
            credentials: 'same-origin'
        });
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.info || 'Failed to load user data')
        }
        const roles = await response.json();
        const container = document.getElementById('rolesCheckboxes');
        container.innerHTML = '';
        roles.forEach(role => {
            const div = document.createElement('div');
            div.className = 'form-check';
            div.innerHTML = `
                <input class="form-check-input" type="checkbox" value="${role.id}" id="role-${role.id}" name="roleIds">
                <label class="form-check-label" for="role-${role.id}">${escapeHtml(role.name)}</label>
            `;
            container.appendChild(div);
        });
    } catch (error) {
        console.error('Failed to load roles', error);
    }
}

async function loadRolesForUpdateForm() {
    try {
        const response = await fetch('/api/roles', {
            credentials: 'same-origin'
        });
        if (!response.ok) {
            throw new Error('Failed to load user data')
        }
        const roles = await response.json();
        const container = document.getElementById('updateRolesCheckboxes');
        container.innerHTML = '';
        roles.forEach(role => {
            const div = document.createElement('div');
            div.className = 'form-check';
            div.innerHTML = `
                <input class="form-check-input update-role" type="checkbox" value="${role.id}" id="update-role-${role.id}">
                <label class="form-check-label" for="update-role-${role.id}">${escapeHtml(role.name)}</label>
            `;
            container.appendChild(div);
        });
    } catch (error) {
        console.error('Failed to load roles for update form', error);
    }
}


async function saveUser() {

    const username = document.getElementById('username').value.trim();
    const lastname = document.getElementById('lastname').value.trim();
    const age = parseInt(document.getElementById('age').value);
    const password = document.getElementById('password').value;

    const roleCheckboxes = document.querySelectorAll('#rolesCheckboxes input[name="roleIds"]:checked');
    const roleIds = Array.from(roleCheckboxes).map(cb => parseInt(cb.value));

    if (!username || !lastname || isNaN(age)) {
        alert('Please fill in username, lastname and age');
        return;
    }

    const payload = {username, lastname, age, password, roleIds};

    try {
        const response = await fetch('/api/admin/users', {
            method: 'POST',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const errText = await response.text();
            throw new Error(errText || 'Failed to create user');
        }


        document.getElementById('createUserForm').reset();
        document.getElementById('users-tab').click();
        loadUsers();
    } catch (error) {
        console.error(error);
        alert(`Error: ${error.message}`);
    }
}


function openDeleteModal(id) {
    fetch(`/api/admin/users/${id}`, {credentials: 'same-origin'})
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    throw new Error(errorData.info || 'Failed to load user data');
                });
            }
            return response.json();
        })
        .then(user => {
            document.getElementById('deleteUserId').value = user.id;
            document.getElementById('modal-userid').textContent = user.id;
            document.getElementById('modal-username').textContent = user.username;
            document.getElementById('modal-lastname').textContent = user.lastname;
            document.getElementById('modal-age').textContent = user.age;
            document.getElementById('modal-roles').textContent = user.roleNames.join(', ');
            deleteModal.show();
        })
        .catch(error => {
            console.error(error);
            alert(error.message || 'Failed to load user data');
        });
}

async function confirmDelete() {
    const userId = document.getElementById('deleteUserId').value;
    try {
        const response = await fetch(`/api/admin/users/${userId}`, {
            method: 'DELETE',
            credentials: 'same-origin',
            headers: {[csrfHeader]: csrfToken}
        });
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.info || 'Failed to load user data')
        }
        deleteModal.hide();
        loadUsers();
    } catch (error) {
        console.error(error);
        alert(error.message || 'Delete failed');
    }
}


function openEditModal(id) {
    fetch(`/api/admin/users/${id}`, {credentials: 'same-origin'})
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => {
                    throw new Error(errorData.info || 'Failed to load user data');
                });
            }
            return response.json();
        })
        .then(user => {
            document.getElementById('updateUserId').value = user.id;
            document.getElementById('update-userid-display').textContent = user.id;
            document.getElementById('update-username').value = user.username;
            document.getElementById('update-lastname').value = user.lastname;
            document.getElementById('update-age').value = user.age;
            document.getElementById('update-password').value = '';

            document.querySelectorAll('#updateRolesCheckboxes .update-role').forEach(checkbox => {
                checkbox.checked = user.roleIds.includes(parseInt(checkbox.value));
            });

            updateModal.show();
        })
        .catch(error => {
            console.error(error);
            alert(error.message || 'Failed to load user data');
        });
}

async function updateUser() {
    const userId = document.getElementById('updateUserId').value;
    const username = document.getElementById('update-username').value.trim();
    const lastname = document.getElementById('update-lastname').value.trim();
    const age = parseInt(document.getElementById('update-age').value);
    const password = document.getElementById('update-password').value;

    const roleCheckboxes = document.querySelectorAll('#updateRolesCheckboxes .update-role:checked');
    const roleIds = Array.from(roleCheckboxes).map(cb => parseInt(cb.value));

    if (!username || !lastname || isNaN(age)) {
        alert('Please fill in username, lastname and age');
        return;
    }

    const payload = {username, lastname, age, password, roleIds};

    try {
        const response = await fetch(`/api/admin/users/${userId}`, {
            method: 'PUT',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(payload)
        });
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.info || 'Failed to load user data')
        }
        updateModal.hide();
        loadUsers();
    } catch (error) {
        console.error(error);
        alert(`Error: ${error.message}`);
    }
}