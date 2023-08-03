# Watchdog! - Service Status Watcher

Watchdog! is a Spring Boot application designed to monitor the status of other services and send notifications to subscribers about service status updates. It provides the ability to send notifications via WhatsApp and Telegram, keeping users informed in real-time.

<details>
    <summary>Overview</summary>
    <img src="/screenshot/Screenshot_2023-08-03_09-26-00.png" alt="Screenshot of watchdog interface" />
    <img src="/screenshot/Screenshot_2023-08-03_09-26-25.png" alt="Screenshot of watchdog interface" />
    <img src="/screenshot/Screenshot_2023-08-03_09-25-11.png" alt="Screenshot of watchdog interface" />
</details>

## Features

- Monitor Service Status: Watchdog! continuously checks the status of specified services.
- Notification Subscriptions: Users can subscribe to receive notifications about the status of specific services.
- WhatsApp Integration: Receive service status updates via WhatsApp notifications.
- Telegram Integration: Receive service status updates via Telegram notifications.

## Getting Started

### Prerequisites

- Java 17 or higher installed on your machine.
- WhatsApp and Telegram accounts for receiving notifications.

### Installation and Setup

1. Clone the Watchdog! repository to your local machine.
2. Copy the `application.properties.example` file to `application.properties` and update the values to match your environment.
3. Modify the ProviderService class to implements your own notification service.
4. Run the application using the following command:

```bash
./mnvw spring-boot:run
```

### Usage

1. Run the Watchdog! application on your local machine or deploy it to a server.
2. Users can subscribe to service status notifications by providing their WhatsApp and Telegram contact information.
3. Watchdog! will automatically monitor the specified services and notify subscribers of any changes in service status.

## Configuration

In the provider menu you need to add telegram and whatsapp service that will send the status notification.
And don't forget to add subscriber in watcher menu

## How to Contribute

We welcome contributions to enhance Watchdog! Please follow these steps:

1. Fork the repository and create a new branch for your contribution.
2. Make your changes and ensure the code is properly tested.
3. Submit a pull request, explaining the changes you made and the problem it solves.

## License

Watchdog! is open-source and released under the [MIT License](LICENSE).

## Support

If you encounter any issues or have any questions, please reach out to us via [nugraha07rizki@gmail.com](mailto:nugraha07rizki@gmail.com).

Happy Monitoring! 🚀
