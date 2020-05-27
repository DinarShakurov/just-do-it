function trySubmit() {
    let email = $("#email")[0]['value']
    let username = $("#username")[0]['value']
    let alias = $("#alias")[0]['value']
    let password = $("#password")[0]['value']

    let errors = []

    if (!username) {
        errors.push("Name must not be null")
    }

    if (!alias){
        errors.push("Alias must not be null")
    }

    if (!email) {
        errors.push("Email must not be null")
    }

    if (!password) {
        errors.push("Password must not be null")
    } else if (password.length < 3 || password.length > 10) {
        errors.push("Password length must be greater than 3 and less than 10")
    }

    if (errors.length !== 0) {
        alert(errors.reduce((acc, val) => acc + "\n" + val))
    } else {
        $("#signUpForm").first().submit()
    }
}