const signupUsername = document.getElementById('signup-username');
const signupPassword = document.getElementById('signup-password');
const signupConfirmPassword = document.getElementById('signup-confirm-password');
const signupButton = document.getElementById('signup-button');

function signUp() {
    let username = signupUsername.value;
    let password = signupPassword.value;
    let confirmPassword = signupConfirmPassword.value;

    if (confirmPassword !== password) {
        return alert('Passwords do not match!');
    }

    // gray out the sign up
    signupUsername.style.color = "#888";
    signupUsername.disabled = true;
    signupPassword.style.color = "#888";
    signupPassword.disabled = true;
    signupConfirmPassword.style.color = "#888";
    signupConfirmPassword.disabled = true;
    signupButton.style.color = "#888";
    signupButton.disabled = true;

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
            window.location.href = "/login";
        })
        .catch(error => {
            if (error.status === 409) {
                const submitStatusElement = document.getElementById("submit-status");
                submitStatusElement.innerText = "The user already exists!";
                submitStatusElement.style.display = "block";
            }
        })
        .finally(() => {
            // somehow we couldn't sign up
            signupUsername.style.color = "";
            signupUsername.disabled = false;
            signupPassword.style.color = "";
            signupPassword.disabled = false;
            signupConfirmPassword.style.color = "";
            signupConfirmPassword.disabled = false;
            signupButton.style.color = "";
            signupButton.disabled = false;
        })
}