//************** CICS Client Classes ******************************************
//
// FILE NAME:    BOSSamp.cpp
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
// BOSSamp.cpp : implementation file
//

#include "stdafx.h"
#include "CclBoss.h"
#include "BOSSamp.h"
#include "ccloeci.h"
#include "time.h"
#include "cicseci.hpp"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// BOSSamp

IMPLEMENT_DYNCREATE(BOSSamp, CCmdTarget)

BOSSamp::BOSSamp()
{
	EnableAutomation();
	
	// To keep the application running as long as an OLE automation 
	//	object is active, the constructor calls AfxOleLockApp.
	
	AfxOleLockApp();
}

BOSSamp::~BOSSamp()
{
	// To terminate the application when all objects created with
	// 	with OLE automation, the destructor calls AfxOleUnlockApp.
	
	AfxOleUnlockApp();
}


void BOSSamp::OnFinalRelease()
{
	// When the last reference for an automation object is released
	// OnFinalRelease is called.  The base class will automatically
	// deletes the object.  Add additional cleanup required for your
	// object before calling the base class.

	CCmdTarget::OnFinalRelease();
}


BEGIN_MESSAGE_MAP(BOSSamp, CCmdTarget)
	//{{AFX_MSG_MAP(BOSSamp)
		// NOTE - the ClassWizard will add and remove mapping macros here.
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

BEGIN_DISPATCH_MAP(BOSSamp, CCmdTarget)
	//{{AFX_DISPATCH_MAP(BOSSamp)
	DISP_PROPERTY_EX(BOSSamp, "Name", GetName, SetName, VT_BSTR)
	DISP_PROPERTY_EX(BOSSamp, "DateOfBirth", GetDateOfBirth, SetDateOfBirth, VT_DATE)
	DISP_PROPERTY_EX(BOSSamp, "YearsService", GetYearsService, SetYearsService, VT_I2)
	DISP_FUNCTION(BOSSamp, "BuildObject", BuildObject, VT_EMPTY, VTS_DISPATCH)
	DISP_FUNCTION(BOSSamp, "BuildCclOBuf", BuildCclOBuf, VT_EMPTY, VTS_DISPATCH)
	//}}AFX_DISPATCH_MAP
END_DISPATCH_MAP()

// Note: we add support for IID_IBOSSamp to support typesafe binding
//  from VBA.  This IID must match the GUID that is attached to the 
//  dispinterface in the .ODL file.

// {AD78EF71-1AAD-11D0-BFEB-0004AC83E1CE}
static const IID IID_IBOSSamp =
{ 0xad78ef71, 0x1aad, 0x11d0, { 0xbf, 0xeb, 0x0, 0x4, 0xac, 0x83, 0xe1, 0xce } };

BEGIN_INTERFACE_MAP(BOSSamp, CCmdTarget)
	INTERFACE_PART(BOSSamp, IID_IBOSSamp, Dispatch)
END_INTERFACE_MAP()

// {AD78EF72-1AAD-11D0-BFEB-0004AC83E1CE}
IMPLEMENT_OLECREATE(BOSSamp, "CclBOS.Sample", 0xad78ef72, 0x1aad, 0x11d0, 0xbf, 0xeb, 0x0, 0x4, 0xac, 0x83, 0xe1, 0xce)

/////////////////////////////////////////////////////////////////////////////
// BOSSamp message handlers

void BOSSamp::BuildObject(LPDISPATCH commArea) 
{
	// TODO: Add your dispatch handler code here

	//
	// Map commArea onto a CICS OLE Buffer object
	//
	CclBuffer pBuffer = CclBuffer(commArea);
	//
	// Extract the variant from the CICS OLE buffer object, and calculate
	// the bounding information
	//
	COleVariant variant = COleVariant(pBuffer.Data());
	unsigned long i,saSize=0;
	long uBound, lBound;
	void * saData;
	for ( i = 1; i<= SafeArrayGetDim (variant.parray ); i++ );
	{
		SafeArrayGetLBound ( variant.parray, i , &lBound );
		SafeArrayGetUBound ( variant.parray, i, &uBound );
		saSize = saSize + uBound - lBound + 1;
	}
	SafeArrayAccessData ( variant.parray, &saData );
	//
	// Create a new CICS C++ Buffer object, prime it with the contents of the
	// safe array
	//
	CclBuf* buffer = new CclBuf(saSize,saData);
	SafeArrayUnlock(variant.parray);
	//
	// Extract the business objects from the buffer
	//
	int dataLength = buffer->dataLength();
	name = CString((char *)buffer->dataArea());
	if (name.GetLength() > dataLength) { name = name.Left(dataLength); }
	int nameLen = name.GetLength() + 1;
	if (nameLen < dataLength)
	{
		yearsService = *(short *)buffer->dataArea(nameLen);
		if (nameLen + sizeof(yearsService) < dataLength )
		{
			doB = *(CTime *)buffer->dataArea(nameLen + sizeof(yearsService));
		}
		else
		{
			doB = CTime::GetCurrentTime();
		}
	}
	else
	{
		yearsService = 0;
		doB = CTime::GetCurrentTime();
	}
	//
	// Detach and release the 'mapped' buffer object
	//
	pBuffer.DetachDispatch();
	pBuffer.ReleaseDispatch();
}

void BOSSamp::BuildCclOBuf(LPDISPATCH commArea) 
{
	// TODO: Add your dispatch handler code here

	//
	// Map commArea onto a CICS OLE Buffer object
	//
	CclBuffer pBuffer = CclBuffer(commArea);
	//
	// Create a new CICS C++ Buffer object, and place the business objects
	// using the appropriate structure, for communicating with the server
	//
	CclBuf* buffer = new CclBuf();
	time_t doBT;
	doBT = doB.GetTime();
	char nu = '\0';
	buffer->assign(sizeof(doBT),&doB);
	buffer->insert(sizeof(yearsService),&yearsService);
	buffer->insert(sizeof(nu),&nu);
	buffer->insert(name.GetLength(),name);
	//
	// Create a safe array to hold the buffer data
	//
	SAFEARRAY * commData;
	SAFEARRAYBOUND sabound[1];
	void * pData;
	sabound[0].lLbound = 0;
	sabound[0].cElements = buffer->dataLength();
	commData = SafeArrayCreate ( VT_UI1, 1, sabound );
	//commData->pvData = (void *)buffer->dataArea();
	SafeArrayAccessData( commData, &pData );
	memcpy(pData,buffer->dataArea(),buffer->dataLength());
	SafeArrayUnlock(commData);
	//
	// Construct the VARIANT object and prime it with the safe array
	//
	VARIANT commVariant;
	VariantInit(&commVariant);
	commVariant.pparray = &commData;
	commVariant.vt = VT_BYREF|VT_ARRAY|VT_UI1;
	//
	// Set the data in the CICS OLE buffer object
	//
	pBuffer.SetData(commVariant);
	//
	// Detach and Release the 'mapped' CICS OLE buffer object
	//
	pBuffer.DetachDispatch();
	pBuffer.ReleaseDispatch();
}

BSTR BOSSamp::GetName() 
{
	CString strResult;
	// TODO: Add your property handler here
	strResult = name;

	return strResult.AllocSysString();
}

void BOSSamp::SetName(LPCTSTR lpszNewValue) 
{
	// TODO: Add your property handler here
	name = lpszNewValue;

}

DATE BOSSamp::GetDateOfBirth() 
{
	// TODO: Add your property handler here

	return (DATE) doB.GetTime();
}

void BOSSamp::SetDateOfBirth(DATE newValue) 
{
	// TODO: Add your property handler here
	doB = CTime(newValue);

}

short BOSSamp::GetYearsService() 
{
	// TODO: Add your property handler here

	return yearsService;
}

void BOSSamp::SetYearsService(short nNewValue) 
{
	// TODO: Add your property handler here
	yearsService = nNewValue;

}
