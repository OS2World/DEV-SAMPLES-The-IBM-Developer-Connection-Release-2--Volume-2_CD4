// Machine generated IDispatch wrapper class(es) created with ClassWizard

#include "stdafx.h"
#include "ccloeci.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif



/////////////////////////////////////////////////////////////////////////////
// CclBuffer properties

/////////////////////////////////////////////////////////////////////////////
// CclBuffer operations

void CclBuffer::SetString(LPCTSTR String)
{
	static BYTE parms[] =
		VTS_BSTR;
	InvokeHelper(0x1, DISPATCH_METHOD, VT_EMPTY, NULL, parms,
		 String);
}

CString CclBuffer::String()
{
	CString result;
	InvokeHelper(0x2, DISPATCH_METHOD, VT_BSTR, (void*)&result, NULL);
	return result;
}

short CclBuffer::Length()
{
	short result;
	InvokeHelper(0x3, DISPATCH_METHOD, VT_I2, (void*)&result, NULL);
	return result;
}

void CclBuffer::AppendString(LPCTSTR String)
{
	static BYTE parms[] =
		VTS_BSTR;
	InvokeHelper(0x4, DISPATCH_METHOD, VT_EMPTY, NULL, parms,
		 String);
}

CString CclBuffer::ExtractString(short offset)
{
	CString result;
	static BYTE parms[] =
		VTS_I2;
	InvokeHelper(0x5, DISPATCH_METHOD, VT_BSTR, (void*)&result, parms,
		offset);
	return result;
}

void CclBuffer::InsertString(short offset, LPCTSTR String)
{
	static BYTE parms[] =
		VTS_I2 VTS_BSTR;
	InvokeHelper(0x6, DISPATCH_METHOD, VT_EMPTY, NULL, parms,
		 offset, String);
}

void CclBuffer::SetLength(short Length)
{
	static BYTE parms[] =
		VTS_I2;
	InvokeHelper(0x7, DISPATCH_METHOD, VT_EMPTY, NULL, parms,
		 Length);
}

void CclBuffer::Overlay(short offset, LPCTSTR String)
{
	static BYTE parms[] =
		VTS_I2 VTS_BSTR;
	InvokeHelper(0x8, DISPATCH_METHOD, VT_EMPTY, NULL, parms,
		 offset, String);
}

void CclBuffer::SetData(const VARIANT& Data)
{
	static BYTE parms[] =
		VTS_VARIANT;
	InvokeHelper(0x9, DISPATCH_METHOD, VT_EMPTY, NULL, parms,
		 &Data);
}

VARIANT CclBuffer::Data()
{
	VARIANT result;
	InvokeHelper(0xa, DISPATCH_METHOD, VT_VARIANT, (void*)&result, NULL);
	return result;
}
