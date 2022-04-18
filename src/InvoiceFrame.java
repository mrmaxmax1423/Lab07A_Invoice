import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class InvoiceFrame extends JFrame
{
    JPanel mainPanel;

    JPanel lineItemInputPanel;
    JTextField productNameInput;
    JTextField productCostInput;
    JTextField amountPurchasedInput;
    JButton addProductButton;

    JPanel customerInputPanel;

    JTextField customerNameInput;
    JTextField customerAddressInput;
    JButton quitButton;
    JButton processButton;

    JPanel displayPanel;
    JTextArea invoiceDisplay;

    ArrayList<LineItem> inputLineItems = new ArrayList<LineItem>();

    public InvoiceFrame()
    {
        mainPanel = new JPanel();

        createLineItemInputPanel();
        mainPanel.add(lineItemInputPanel, BorderLayout.NORTH);

        createCustomerInputPanel();
        mainPanel.add(customerInputPanel, BorderLayout.CENTER);

        createDisplayPanel();
        mainPanel.add(displayPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setSize(800,550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void createLineItemInputPanel()
    {
        lineItemInputPanel = new JPanel();

        productNameInput = new JTextField("Product Name",10);
        productCostInput = new JTextField("Cost", 6);
        amountPurchasedInput = new JTextField("Amount",6);

        addProductButton = new JButton("Input Product + Quantity");
        addProductButton.addActionListener((ActionEvent ae) -> storeLineItem(new Product(productNameInput.getText(), Double.valueOf(productCostInput.getText())), Integer.valueOf(amountPurchasedInput.getText())));
        lineItemInputPanel.add(productNameInput);
        lineItemInputPanel.add(productCostInput);
        lineItemInputPanel.add(amountPurchasedInput);
        lineItemInputPanel.add(addProductButton);
    }

    public void createCustomerInputPanel()
    {
        customerInputPanel = new JPanel();

        customerNameInput = new JTextField("Customer Name",10);
        customerAddressInput = new JTextField("Customer Address" ,15);

        quitButton = new JButton("Quit");
        quitButton.addActionListener((ActionEvent ae) -> System.exit(0));
        processButton = new JButton("Create Invoice");
        processButton.addActionListener((ActionEvent ae) -> createInvoice(new Customer(customerNameInput.getText(), customerAddressInput.getText()), inputLineItems));

        customerInputPanel.add(customerNameInput);
        customerInputPanel.add(customerAddressInput);
        customerInputPanel.add(processButton);
        customerInputPanel.add(quitButton);
    }

    public void createDisplayPanel()
    {
        displayPanel = new JPanel();
        invoiceDisplay = new JTextArea(15,40);

        invoiceDisplay.setFont(new java.awt.Font("Serif", 0, 20));
        displayPanel.add(invoiceDisplay);
    }

    public void createInvoice(Customer customer, ArrayList<LineItem> lineItems)
    {
        if(inputLineItems.size() > 0)
        {
            double lineItemPrice;
            double totalPrice = 0;
            String customerName = customer.getCustomerName();
            String customerAddress = customer.getCustomerAddress();
            invoiceDisplay.append("                                             Invoice \n");
            invoiceDisplay.append(customerName + "\n" + customerAddress + "\n");
            invoiceDisplay.append("===================================================\n");
            invoiceDisplay.append("Item,  QTY,  Price, Total\n");
            for(LineItem lineItem : inputLineItems)
            {
                lineItemPrice = ((lineItem.getQuantity()) * lineItem.getUnitPrice());
                totalPrice += lineItemPrice;
                invoiceDisplay.append(lineItem.getProductName() + ",  " + lineItem.getQuantity() + ",  $" + (String.format("%.2f",(lineItem.getUnitPrice()))) +",  $" + (String.format("%.2f",lineItemPrice)) +  "\n");
            }
            invoiceDisplay.append("===================================================\n");
            invoiceDisplay.append("Amount Due: $" + (String.format("%.2f",totalPrice)));
        }
    }

    public void storeLineItem(Product newProduct, int amount)
    {
        LineItem addedProduct = new LineItem(newProduct.getProductName(), newProduct.getUnitPrice(),amount);
        inputLineItems.add(addedProduct);
    }
}
