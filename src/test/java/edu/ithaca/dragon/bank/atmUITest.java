package edu.ithaca.dragon.bank;

public class atmUITest {

    public static void main(String[] args) {
        CentralBank testCentralBank = CentralBankTest.buildTestObject();
        BasicAPI testAPI = new BankSoftware(testCentralBank);
        AtmUI testUI = new AtmUI(testAPI);

        testUI.loginPage();

    }
}
