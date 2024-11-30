import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MeasuringDeviceApp {
    private JFrame frame;
    private DefaultListModel<String> deviceListModel;
    private ArrayList<MeasuringDevice> devices;

    public MeasuringDeviceApp() {
        devices = new ArrayList<>();

        // Створення основного вікна
        frame = new JFrame("Measuring Device Management");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Панель для відображення списку пристроїв
        JPanel leftPanel = new JPanel(new BorderLayout());
        deviceListModel = new DefaultListModel<>();
        JList<String> deviceList = new JList<>(deviceListModel);
        JScrollPane deviceScrollPane = new JScrollPane(deviceList);
        leftPanel.add(new JLabel("Devices:"), BorderLayout.NORTH);
        leftPanel.add(deviceScrollPane, BorderLayout.CENTER);

        JButton viewDeviceButton = new JButton("View Device");
        leftPanel.add(viewDeviceButton, BorderLayout.SOUTH);

        // Панель для кнопок
        JPanel buttonPanel = new JPanel();
        JButton addDeviceButton = new JButton("Add Device");
        buttonPanel.add(addDeviceButton);

        // Панель для відображення інформації про пристрій
        JPanel rightPanel = new JPanel(new BorderLayout());
        JTextArea deviceInfoArea = new JTextArea();
        deviceInfoArea.setEditable(false);
        JScrollPane infoScrollPane = new JScrollPane(deviceInfoArea);
        rightPanel.add(new JLabel("Device Info:"), BorderLayout.NORTH);
        rightPanel.add(infoScrollPane, BorderLayout.CENTER);

        JButton addItemButton = new JButton("Add Item");
        rightPanel.add(addItemButton, BorderLayout.SOUTH);

        // Розташування панелей
        frame.setLayout(new BorderLayout());
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(rightPanel, BorderLayout.CENTER);

        // Слухачі подій
        addDeviceButton.addActionListener(e -> addDevice());
        viewDeviceButton.addActionListener(e -> {
            int selectedIndex = deviceList.getSelectedIndex();
            if (selectedIndex != -1) {
                displayDeviceInfo(selectedIndex, deviceInfoArea);
            } else {
                JOptionPane.showMessageDialog(frame, "Select a device to view.");
            }
        });
        addItemButton.addActionListener(e -> {
            int selectedIndex = deviceList.getSelectedIndex();
            if (selectedIndex != -1) {
                addItemToDevice(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(frame, "Select a device to add items.");
            }
        });

        frame.setVisible(true);
    }

    private void addDevice() {
        // Діалог для введення назви нового пристрою
        String deviceName = JOptionPane.showInputDialog(frame, "Enter Device Name:");
        if (deviceName != null && !deviceName.trim().isEmpty()) {
            // Перевірка унікальності імені пристрою
            boolean exists = devices.stream().anyMatch(device -> device.getDeviceName().equalsIgnoreCase(deviceName));
            if (exists) {
                JOptionPane.showMessageDialog(frame, "A device with this name already exists! Please choose a different name.");
            } else {
                MeasuringDevice newDevice = new MeasuringDevice(deviceName, new ArrayList<>());
                devices.add(newDevice);
                deviceListModel.addElement(deviceName);
                JOptionPane.showMessageDialog(frame, "Device created successfully.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Device name cannot be empty.");
        }
    }


    private void displayDeviceInfo(int index, JTextArea deviceInfoArea) {
        MeasuringDevice device = devices.get(index);
        StringBuilder info = new StringBuilder("Device Name: " + device.getDeviceName() + "\n");
        info.append("Items:\n");
        for (PurchasableItem item : device.getItems()) {
            info.append(item).append("\n");
        }
        deviceInfoArea.setText(info.toString());
    }

    private void addItemToDevice(int index) {
        MeasuringDevice device = devices.get(index);

        // Діалог для введення даних нового елемента
        JTextField nameField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField supplierField = new JTextField();
        Object[] message = {
                "Item Name:", nameField,
                "Quantity:", quantityField,
                "Supplier:", supplierField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add New Item", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                String supplier = supplierField.getText();
                PurchasableItem newItem = new PurchasableItem(name, quantity, supplier);
                device.addNewItem(newItem);
                JOptionPane.showMessageDialog(frame, "Item added successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input for quantity!");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MeasuringDeviceApp::new);
    }
}
