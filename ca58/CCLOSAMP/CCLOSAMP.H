//--------------------------------------------------------------------------
// cclosamp.h : main header file for the CCLOSAMP application
//
//--------------------------------------------------------------------------

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"		// main symbols
#include "cicseci.hpp"      // CICS Client ECI class library

//--------------------------------------------------------------------------
// CcloSampApp:
// See cclosamp.cpp for the implementation of this class
//--------------------------------------------------------------------------
class CcloECI; // forward declaration

class CcloSampApp : public CWinApp
{
public:
	CcloSampApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CcloSampApp)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation

	//{{AFX_MSG(CcloSampApp)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


//---------------------------------------------------------------------------
// CcloSamp command target
//---------------------------------------------------------------------------

class CcloSamp : public CCmdTarget
{
	DECLARE_DYNCREATE(CcloSamp)

	CcloSamp();           // protected constructor used by dynamic creation

// Attributes
public:
	CclECI* m_pECI; // pointer to ECI subclass
	CclConn* m_pConn; // pointer to CICS Connect object

	// CICS server details
	CString m_server;
	CString m_userid;
	CString m_password;


// Operations
public:
	void throwException( CString string );

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CcloSamp)
	public:
	virtual void OnFinalRelease();
	//}}AFX_VIRTUAL

// Implementation
protected:
	virtual ~CcloSamp();

	// Generated message map functions
	//{{AFX_MSG(CcloSamp)
		// NOTE - the ClassWizard will add and remove member functions here.
	//}}AFX_MSG

	DECLARE_MESSAGE_MAP()
	DECLARE_OLECREATE(CcloSamp)

	// Generated OLE dispatch map functions
	//{{AFX_DISPATCH(CcloSamp)
	afx_msg void SetServer(LPCTSTR server, LPCTSTR userid, LPCTSTR password);
	afx_msg BSTR GetInfo();
	afx_msg BSTR GetServerName(short index);
	afx_msg BSTR GetServerDesc(short index);
	afx_msg DATE GetDate();
	//}}AFX_DISPATCH
	DECLARE_DISPATCH_MAP()
	DECLARE_INTERFACE_MAP()
};

