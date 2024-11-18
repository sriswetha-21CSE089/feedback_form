<%-- 
    Document   : form
    Created on : 17 Nov, 2024, 4:34:59 PM
    Author     : Swetha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback Form</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">
    <link href="style.css" rel="stylesheet">
</head>
<body>
    <div class="form-container">
        <h1>Feedback Form</h1>
        <form id="uploadForm" action="display" method="POST" onsubmit="return confirmSubmission()">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" placeholder="Enter your name" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" placeholder="Enter your email" required>

            <label for="message">Message:</label>
            <textarea id="message" name="message" placeholder="Enter your message" required></textarea>

            <label for="imageFile">Select Image:</label>
            <input type="file" id="imageFile" name="imageFile" accept="image/*" >

            <input type="hidden" id="base64Image" name="base64Image">
            <input type="submit" value="SUBMIT" class="submit-btn">
        </form>
    </div>

    <script>
        document.getElementById('imageFile').addEventListener('change', function(event) {
            const file = event.target.files[0];
            const reader = new FileReader();
            
            reader.onloadend = function() {
                document.getElementById('base64Image').value = reader.result.split(',')[1];
            };

            if (file) {
                reader.readAsDataURL(file);
            }
        });

        function confirmSubmission() {
            return window.confirm("Are you sure you want to submit the form and send the email?");
        }
    </script>
</body>
</html>