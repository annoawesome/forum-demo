const signupUsername = document.getElementById('signup-username');
const signupPassword = document.getElementById('signup-password');
const signupConfirmPassword = document.getElementById('signup-confirm-password');

function signUp() {
    let username = signupUsername.value;
    let password = signupPassword.value;
    let confirmPassword = signupConfirmPassword.value;

    if (confirmPassword !== password) {
        return alert('Passwords do not match!');
    }

    fetch('/users/signup', {
        method: 'POST',
        body: JSON.stringify({
            username: username,
            password: password,

        }),
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('content')
        }
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                return Promise.reject(response);
            }
        })
        .then(data => {
            if (data.success) {
                window.location.redirect("/login");
            }
        })
        .catch(error => {
            if (error.status === 409) {
                const submitStatusElement = document.getElementById("submit-status");
                submitStatusElement.innerText = "The user already exists!";
                submitStatusElement.style.display = "block";
            }
        })
}