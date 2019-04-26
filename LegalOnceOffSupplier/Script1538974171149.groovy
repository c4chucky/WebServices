import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

def body = MapOnceOffInvoiceBody()
def response = WS.sendRequest(MapOnceOffInvoiceBody())
def bodyText = response.getResponseText()

println response
println bodyText

def MapOnceOffInvoiceBody() {
	
	int invNumber = Math.random()*1000000
	int invAmount = Math.random()*1000000
	//double inv = invAmount.round(2)
	String amnt = invAmount.toString()
	
return findTestObject('addSupplierInvoice', [
                ('SupplierId') : '',
                ('PastelCode') : '',
                ('SupplierName') : CustomKeywords.'RandomNameGenerator.randomName'(),
                ('BankAccountId') : 'AA4100FF-8E23-C189-245C-08D5F1FC702E',
                ('InvoiceNumber') : 'INV'+invNumber,
                ('InvoiceDate') : '2018-10-05 00:00:00.000',
                ('PurchaseOrderNumber') : '',
                ('TotalAmount') : invAmount,
                ('TotalTaxableAmount') : invAmount,
                ('TotalNonTaxableAmount') : "",
                ('InvoiceCreator') : 'Charles Brown',
                ('InvoiceItems') : 
								[('Id') : '353ABD8F-E7BD-C179-2993-08D629BC176B',
                                ('Description') : '${P0123456}',
                                ('ExpenditureLedgerCode') : '2202-002-000-PSL-GNRL',
                                ('ExpenseTypeId') : '1',
                                ('DepartmentId') : '128',
                                ('DepartmentDescription') : '',
                                ('DepartmentCode') : '045',
                                ('IsBudgetedFor') : '1',
                                ('Amount') : invAmount,
                                ('BankLedgerCode') : '8002-014-000-PSL-GNRL',
                                ('TaxCode') : '20',
                                ('ProjectCode') : 'LM01',
                                ('TaxableAmount') : '',
                                ('NontaxableAmount') : ''],
                ('SupplierBusinessEntityCategoryDivisionId') : '9',
                ('SupplierPaymentReference') : 'Clientele',
                ('SupplierVenueCode') : '',
                ('SupplierInvoiceRecordStoreId') : 'A92D5BBC-7DB5-C99A-6AC8-08D629B9C2AE',
                ('SupplierQuotationRecordStoreId') : '',
                ('SupplierProformaRecordStoreId') : '',
                ('IsUrgent') : '0',
                ('PrimaryInvoiceDocumentTypeId') : '0',
                ('IsDeposit') : '0',
                ('ApprovalRule') : [],
                ('BankName') : '',
                ('AccountNumber') : '',
                ('AccountType') : '',
                ('BranchCode') : '',
                ('SourceSystemId') : ''])
	
}