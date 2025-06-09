<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Smart Bee House</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h1>Smart Bee House Backend Test</h1>
    <div id="result"></div>

    <script>
        $(document).ready(function() {
            // Test the API endpoint
            $.ajax({
                url: 'api/test',
                method: 'GET',
                success: function(response) {
                    $('#result').html('Response from server: ' + response.message);
                },
                error: function(xhr, status, error) {
                    $('#result').html('Error: ' + error);
                }
            });
        });
    </script>
</body>
</html> 