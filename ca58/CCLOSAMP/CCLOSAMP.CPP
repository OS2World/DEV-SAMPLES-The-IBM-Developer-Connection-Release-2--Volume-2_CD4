//---------------------------------------------------------------------------
// cclosamp.cpp : Contains the implementations of the CcloSampApp application
//				  and the CcloSamp OLE object.
//---------------------------------------------------------------------------

#include "stdafx.h"
#include "cclosamp.h"
#include "CcloDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

//---------------------------------------------------------------------------
// CcloSampApp implementation
//---------------------------------------------------------------------------

BEGIN_MESSAGE_MAP(CcloSampApp, CWinApp)
	//{{AFX_MSG_MAP(CcloSampApp)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG
	ON_COMMAND(ID_HELP, CWinApp::OnHelp)
END_MESSAGE_MAP()

//---------------------------------------------------------------------------
// CcloSampApp construction
//---------------------------------------------------------------------------

CcloSampApp::CcloSampApp()
{
	// Place all significant initialization in InitInstance
}


CcloSampApp theApp;	 // The one and only CcloSampApp object

//---------------------------------------------------------------------------
// CcloSampApp initialization
//---------------------------------------------------------------------------

BOOL CcloSampApp::InitInstance()
{
	// Initialize OLE libraries
	if (!AfxOleInit())
	{
		AfxMessageBox(IDP_OLE_INIT_FAILED);
		return FALSE;
	}

	// Standard initialization
	// If you are not using these features and wish to reduce the size
	//  of your final executable, you should remove from the following
	//  the specific initialization routines you do not need.

#ifdef _AFXDLL
	Enable3dControls();			// Call this when using MFC in a shared DLL
#else
	Enable3dControlsStatic();	// Call this when linking to MFC statically
#endif

	// Parse the command line to see if launched as OLE server
	if (RunEmbedded() || RunAutomated())
	{
		// Register all OLE server (factories) as running.  This enables the
		//  OLE libraries to create objects from other applications.
		COleTemplateServer::RegisterAll();

		// Application was run with /Embedding or /Automation.  Don't show the
		//  main window in this case.
		return TRUE;
	}

	// When a server application is launched stand-alone, it is a good idea
	//  to update the system registry in case it has been damaged.
	COleObjectFactory::UpdateRegistryAll();

	CcloSampDlg dlg;
	m_pMainWnd = &dlg;
	int nResponse = dlg.DoModal();
	
	// Since the dialog has been closed, return FALSE so that we exit the
	//  application, rather than start the application's message pump.
	return FALSE;
}


//---------------------------------------------------------------------------
// CcloSamp implementation
//---------------------------------------------------------------------------

IMPLEMENT_DYNCREATE(CcloSamp, CCmdTarget)

CcloSamp::CcloSamp()
{
	EnableAutomation();
	
	// To keep the application running as long as an OLE automation 
	//	object is active, the constructor calls AfxOleLockApp.
	AfxOleLockApp();

	// Get CICS Client ECI top-level object.
	m_pECI =  CclECI::instance();
	m_pConn = NULL;
}

CcloSamp::~CcloSamp()
{
	// To terminate the application when all objects created with
	// 	with OLE automation, the destructor calls AfxOleUnlockApp.
	
	AfxOleUnlockApp();
}


void CcloSamp::OnFinalRelease()
{
	// When the last reference for an automation object is released
	// OnFinalRelease is called.  The base class will automatically
	// deletes the object.  Add additional cleanup required for your
	// object before calling the base class.

	// Delete CICS Client objects
	delete m_pConn;
	delete m_pECI;

	CCmdTarget::OnFinalRelease();
}


BEGIN_MESSAGE_MAP(CcloSamp, CCmdTarget)
	//{{AFX_MSG_MAP(CcloSamp)
		// NOTE - the ClassWizard will add and remove mapping macros here.
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

BEGIN_DISPATCH_MAP(CcloSamp, CCmdTarget)
	//{{AFX_DISPATCH_MAP(CcloSamp)
	DISP_FUNCTION(CcloSamp, "SetServer", SetServer, VT_EMPTY, VTS_BSTR VTS_BSTR VTS_BSTR)
	DISP_FUNCTION(CcloSamp, "GetInfo", GetInfo, VT_BSTR, VTS_NONE)
	DISP_FUNCTION(CcloSamp, "GetServerName", GetServerName, VT_BSTR, VTS_I2)
	DISP_FUNCTION(CcloSamp, "GetServerDesc", GetServerDesc, VT_BSTR, VTS_I2)
	DISP_FUNCTION(CcloSamp, "GetDate", GetDate, VT_DATE, VTS_NONE)
	//}}AFX_DISPATCH_MAP
END_DISPATCH_MAP()

// Note: we add support for IID_IcloSamp to support typesafe binding
//  from VBA.  This IID must match the GUID that is attached to the 
//  dispinterface in the .ODL file.

// {427208B8-5754-11D0-9DF8-0004AC3638DC}
static const IID IID_IcloSamp =
{ 0x427208b8, 0x5754, 0x11d0, { 0x9d, 0xf8, 0x0, 0x4, 0xac, 0x36, 0x38, 0xdc } };

BEGIN_INTERFACE_MAP(CcloSamp, CCmdTarget)
	INTERFACE_PART(CcloSamp, IID_IcloSamp, Dispatch)
END_INTERFACE_MAP()

// {427208B9-5754-11D0-9DF8-0004AC3638DC}
IMPLEMENT_OLECREATE(CcloSamp, "Ccl.Sample", 0x427208b9, 0x5754, 0x11d0, 0x9d, 0xf8, 0x0, 0x4, 0xac, 0x36, 0x38, 0xdc)

//--------------------------------------------------------------------------
// CcloSamp message handlers (i.e. implementations for methods on OLE
// object)
//--------------------------------------------------------------------------

BSTR CcloSamp::GetServerName(short index) 
{
	// Get requested CICS server name from ECI object
	CString strResult = m_pECI->serverName(index);
	return strResult.AllocSysString();
}

BSTR CcloSamp::GetServerDesc(short index) 
{
	// Get requested CICS server description from ECI object
	CString strResult = m_pECI->serverDesc(index);
	return strResult.AllocSysString();
}

void CcloSamp::SetServer(LPCTSTR server, LPCTSTR userid, LPCTSTR password) 
{
	// Validate input server name length
	if ( strlen(server) < 1	 ||
		 strlen(server) > 8 ) 
		throwException("server name invalid");

	// Save the supplied CICS server name, userid, password
	m_server = server;
	m_userid = userid;
	m_password = password;

	// (Re)create CICS Client Connect object for specified server
	delete m_pConn;
	m_pConn = new CclConn( server, userid, password );
}

DATE CcloSamp::GetDate() 
{
	// Create CICS Client Flow object 
	CclFlow flow( Ccl::sync );

	// Check the user already set up a connection 
	if ( m_pConn == NULL ) {
		throwException("No connection details");
		return (DATE) 0;
	}

	// Construct the commarea
	struct {
        char date[9];
        char time[9];
        char applid[8];
        char info[40];
    } commStruct;
	// Create CICS Client Buffer object to contain the commarea
	CclBuf commArea( sizeof(commStruct), &commStruct );

	try { // handle exceptions

		// Make link call to CICS server
	    m_pConn->link( flow, "ECISAMP", &commArea );

	} catch ( CclException exception )	{
		// Get the exception text string
		CString string = exception.exCodeText();
	    // Append the CICS abend code (if any) to the exception string
	    if ( exception.exObject() )
	    	string = string + "  " + ((CclFlow*)(exception.exObject()))->abendCode();
	    throwException( string );
	}

	// Extract date and time from the commarea, and construct OLE/VB date
	SYSTEMTIME t;
	sscanf( commStruct.date, "%2hd%2hd%4hd", &t.wDay, &t.wMonth, &t.wYear ); 
	sscanf( commStruct.time, "%2hd%2hd%2hd", &t.wHour, &t.wMinute, &t.wSecond );
	COleDateTime date(t);

	return (DATE) date;
}

BSTR CcloSamp::GetInfo() 
{
 	CString strResult;
	// Create CICS Client Flow object
	CclFlow flow( Ccl::sync );

	// Check the user already set up a connection
	if ( m_pConn == NULL ) {
		throwException("No connection details");
		return strResult.AllocSysString();
	}

	// Construct the commarea
	struct {
        char date[9];
        char time[9];
        char applid[8];
        char info[40];
    } commStruct;
	// Create CICS Client Buffer object to contain the commarea
	CclBuf commArea( sizeof(commStruct), &commStruct );

	try { // handle exceptions

		// Make link call to CICS server
	    m_pConn->link( flow, "ECISAMP", &commArea );

	} catch ( CclException exception )	{
		// Get the exception text string
		CString string = exception.exCodeText();
	    // Append the CICS abend code (if any) to the exception string
	    if ( exception.exObject() )
	    	string = string + "  " + ((CclFlow*)(exception.exObject()))->abendCode();
	    throwException( string );
	}

	// Tidy up the CICS Client Flow object
	flow.forceReset();
	// Extract data from commarea and return as OLE/VB string
	strResult = commStruct.info;
	return strResult.AllocSysString();
}

// Utility method to generate OLE exception (provides friendly debug
// for Visual Basic users
void CcloSamp::throwException( CString string )
{
	// Class method to throw OLE exception
	CString exString = "Sample Automation Server: ";
	exString += string;
	// Create and throw an OLE exception to Visual Basic etc
	COleDispatchException* pException =  new COleDispatchException( exString, 200, 1000 );
	pException->m_scError = 0;
	throw( pException );
}

