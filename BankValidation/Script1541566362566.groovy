import static org.assertj.core.api.Assertions.*
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
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

//RequestObject webCall = findTestObject('Object Repository/BankVal')

def data = findTestData("BankVal")
for (def index : (1..data.getRowNumbers())) {
	GlobalVariable.index = index
	
	'Build up the webcall from test data'
	RequestObject webCall = BuildRequestBody(index)
	
	'Send the web request'
	def response = WS.sendRequest(webCall)
	
	'Validate the response'
	WS.verifyResponseStatusCode(response, 200)
	println ParseJsonResponse(response) 

	def bankValResult = ParseJsonResponse(response)
			
	GlobalVariable.column = 19
		
	for (int i=0;i<bankValResult.length;i++) {
		CustomKeywords.'writeToExcel.WriteExcel.demoKey'(bankValResult[i])
		GlobalVariable.column++
	}
	
}

CustomKeywords.'moveDataFile.MoveDataFileToProcessed.moveDataFileToProcessed'()

def BuildRequestBody(int rowId) {
	
	return findTestObject('BankVal', [('AccountNumber') : findTestData('BankVal').getValue('AccountNumber', rowId),
		('BranchCode') : findTestData('BankVal').getValue('BranchCode', rowId),
		('AccountType') : findTestData('BankVal').getValue('AccountType', rowId),
		('BankName') : findTestData('BankVal').getValue('BankName', rowId),		
		('SourceSystemId') : findTestData('BankVal').getValue('SourceSystemId', rowId),
		('IsLegal') : findTestData('BankVal').getValue('IsLegal', rowId),
		('IdentityOrPassportOrRegistrationNumber') : findTestData('BankVal').getValue('IdentityOrPassportOrRegistrationNumber', rowId),
		('Initials') : findTestData('BankVal').getValue('Initials', rowId),
		('SurnameOrCompanyName') : findTestData('BankVal').getValue('SurnameOrCompanyName', rowId),
		('tel_number') : findTestData('BankVal').getValue('tel_number', rowId),
		('email_address') : findTestData('BankVal').getValue('email_address', rowId),
		('BusinessEntity') : findTestData('BankVal').getValue('BusinessEntity', rowId),
		('ValidateSoftyComp') : findTestData('BankVal').getValue('ValidateSoftyComp', rowId),
		('ValidateFraudster') : findTestData('BankVal').getValue('ValidateFraudster', rowId),
		('ValidateD3BlackList') : findTestData('BankVal').getValue('ValidateD3BlackList', rowId),
		('ValidateAvsr') : findTestData('BankVal').getValue('ValidateAvsr', rowId),
		('FraudsterRejectionCodesToIgnore') : [findTestData('BankVal').getValue('FraudsterRejectionCodesToIgnore', rowId)],
		('AvsrBanksToIgnore') : [findTestData('BankVal').getValue('AvsrBanksToIgnore', rowId)],
		('TimeoutInSeconds') : findTestData('BankVal').getValue('TimeoutInSeconds', rowId)])
}

def ParseJsonResponse(ResponseObject response) {
	def jsonSlurper = new JsonSlurper()
	def result = response.getResponseBodyContent()
	def jsonResponse = jsonSlurper.parseText(result)
	
	println result
	println jsonResponse
	println "${jsonResponse.AvsrResult.AccountLengthMatch}"
	println "${jsonResponse.IsValid}"
	
	
		
	String[] bankValResults = ["${jsonResponse.SoftyCompResult.Message}",
	"${jsonResponse.FraudsterResult.Message}",
	"${jsonResponse.D3BlackListResult.Message}",
	"${jsonResponse.AvsrResult.Message}",
	"${jsonResponse.AvsrResult.DoesPhoneMatch}",
	"${jsonResponse.AvsrResult.DoesEmailMatch}", //'Account passed CDV.'
	]
	
	/*	
	Map bankValResults = [:]
		bankValResults['SoftyCompResult'] = "${jsonResponse.SoftyCompResult.Message}"
		bankValResults['FraudsterResult'] = "${jsonResponse.FraudsterResult.Message}"
		bankValResults['D3BlackListResult'] = "${jsonResponse.D3BlackListResult.Message}"
		bankValResults['AvsrResult'] = "${jsonResponse.AvsrResult.Message}"
	*/
	return bankValResults
		
	//CustomKeywords.'writeToExcel.WriteExcel.demoKey'("${jsonResponse.Message}")
	
	/*assertThat(response.getResponseText()).contains('Sucessful - AVS SUCCESS')
	assertThat(response.getResponseText()).contains('Sucessful - AVS SUCCESS')
	assertThat(response.getResponseText()).contains('D3 Validation Successful')
	*/
	
	
}
