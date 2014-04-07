//************** CICS Client Classes ******************************************
//
// FILE NAME:    BOSSamp.h
//
// DESCRIPTION:  OO CICS Client sample, C++ source
//
// (C) Copyright International Business Machines Corp. 1995,1996
//                                                                    
// This is an example application which demonstrates how to use the OO CICS
// Client External Call Interface (ECI). It is intended for use with Microsoft
// VisualC++ on Windows 95 and Windows NT.
//                                                                    
// It does not demonstrate all the techniques required for a large application.
// It is not a template and should not be used as the foundation for your next
// next mission critical application.      
//                                                                    
//*****************************************************************************
// BOSSamp.h : header file
//



/////////////////////////////////////////////////////////////////////////////
// BOSSamp command target
//#include "ccloeci.h"

class BOSSamp : public CCmdTarget
{
	DECLARE_DYNCREATE(BOSSamp)

	BOSSamp();           // protected constructor used by dynamic creation

// Attributes
public:
	CString name;
	CTime doB;
	short yearsService;
//	CclOBuf pBuffer;

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(BOSSamp)
	public:
	virtual void OnFinalRelease();
	//}}AFX_VIRTUAL

// Implementation
protected:
	virtual ~BOSSamp();

	// Generated message map functions
	//{{AFX_MSG(BOSSamp)
		// NOTE - the ClassWizard will add and remove member functions here.
	//}}AFX_MSG

	DECLARE_MESSAGE_MAP()
	DECLARE_OLECREATE(BOSSamp)

	// Generated OLE dispatch map functions
	//{{AFX_DISPATCH(BOSSamp)
	afx_msg BSTR GetName();
	afx_msg void SetName(LPCTSTR lpszNewValue);
	afx_msg DATE GetDateOfBirth();
	afx_msg void SetDateOfBirth(DATE newValue);
	afx_msg short GetYearsService();
	afx_msg void SetYearsService(short nNewValue);
	afx_msg void BuildObject(LPDISPATCH commArea);
	afx_msg void BuildCclOBuf(LPDISPATCH commArea);
	//}}AFX_DISPATCH
	DECLARE_DISPATCH_MAP()
	DECLARE_INTERFACE_MAP()
};

/////////////////////////////////////////////////////////////////////////////
