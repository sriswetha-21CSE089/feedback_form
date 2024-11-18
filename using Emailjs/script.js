document.getElementById('feedbackForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent form submission

    const name = document.getElementById('name').value;
    console.log(name);
    const email = document.getElementById('email').value;
    console.log(email);
    const message = document.getElementById('message').value;
    console.log(message);
    const attachment = document.getElementById('attachment').files[0];
    console.log(attachment);
    // Simple email validation pattern
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

    // Validation checks
    if (!name || !email || !message) {
        alert('Please fill out all required fields.');
        return;
    }

    if (!emailPattern.test(email)) {
        alert('Please enter a valid email address.');
        return;
    }
    const isConfirmed = confirm("Are you sure you want to submit the feedback?");
    if (!isConfirmed) {
        return; // If the user cancels, stop further execution
    }
    // Read the file as base64 if there is an attachment
    if (attachment) {
        const reader = new FileReader();
        reader.onload = function() {
            const base64String = reader.result.split(',')[1]; // Extract base64 data
            console.log(base64String);
            // Send email using EmailJS
            emailjs.send("service_gb8yqya", "template_t23i9jb", {
                name: name,
                email: email,
                message: message,
                attachment: base64String
            })
            .then(function(response) {
                alert('Feedback submitted successfully!');
                document.getElementById('feedbackForm').reset();
                console.log('SUCCESS!', response.status, response.text);
            }, function(error) {
                alert('Failed to send feedback. Please try again later.');
                console.log('FAILED...', error);
            });
        };
        reader.readAsDataURL(attachment);
    } else {
        // Send email without an attachment
        emailjs.send("service_gb8yqya", "template_t23i9jb", {
            name: name,
            email: email,
            message: message
        })
        .then(function(response) {
            alert('Feedback submitted successfully!');
            console.log('SUCCESS!', response.status, response.text);
            document.getElementById('feedbackForm').reset();
        }, function(error) {
            alert('Failed to send feedback. Please try again later.');
            console.log('FAILED...', error);
        });
    }

    return false; // Prevent actual form submission
});
