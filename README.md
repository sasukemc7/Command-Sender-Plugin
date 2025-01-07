# CommandSender Plugin

## Overview

The CommandSender plugin allows you to send commands to the Minecraft server console via HTTP requests. This plugin is useful for automating server management tasks or integrating with other applications.

## Features

- Execute server commands via HTTP requests.
- Secure access with a configurable password.
- Reload configuration without restarting the server.

## Installation

1. **Download and Install the Plugin:**
   - Place the `CommandSender.jar` file in the `plugins` directory of your Minecraft server.

2. **Configure the Plugin:**
   - Edit the `config.yml` file located in the `plugins/CommandSender` directory.
   - Set the desired port and password for the HTTP server.

   ```yaml
   port: 4567
   password: your_secure_password
   ```
3. **Start the Server:¨**  
   - Start or restart your Minecraft server to load the plugin.

## Usage (Example with Node.JS)

You can use Node.js and the Axios library to send commands to the Minecraft server. Below is an example script:

```js
import axios from 'axios';

const command = 'say ¡Hello World!'; // The command you want to execute
const password = 'testpass'; // Password
const url = 'http://37.27.125.25:25818/execute'; // The URL of the HTTP server

axios.post(url, null, {
    headers: {
        'password': password
    },
    params: {
        'command': command
    }
})
.then(response => {
    console.log('Response:', response.data);
})
.catch(error => {
    console.error('Error:', error.response ? error.response.data : error.message);
});
```

**Explanation**
- **Command:** The command to be executed on the Minecraft server.
- **Password:** The password set in the `config.yml` file to authenticate the request.
- **URL:** The URL of the HTTP server running on your Minecraft serv

**Running the Script**
1. **Install Axios:**
   - `npm install axios`
2. **Run the Script:**
   - `node your_script.js`
Replace `your_script.js` with the name of your script file.

## Configuration
The `config.yml` file allows you to configure the plugin:

- **port:** The port on which the HTTP server will run.
- **password:** The password required to authenticate HTTP request

Example `config.yml`:
```yml
port: 4567
password: your_secure_password
```

## License
This project is licensed under the **MIT License**. See the **LICENSE** file for details.
