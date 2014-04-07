// Machine generated IDispatch wrapper class(es) created with ClassWizard
/////////////////////////////////////////////////////////////////////////////
// CclBuffer wrapper class

class CclBuffer : public COleDispatchDriver
{
public:
	CclBuffer() {}		// Calls COleDispatchDriver default constructor
	CclBuffer(LPDISPATCH pDispatch) : COleDispatchDriver(pDispatch) {}
	CclBuffer(const CclBuffer& dispatchSrc) : COleDispatchDriver(dispatchSrc) {}

// Attributes
public:

// Operations
public:
	void SetString(LPCTSTR String);
	CString String();
	short Length();
	void AppendString(LPCTSTR String);
	CString ExtractString(short offset);
	void InsertString(short offset, LPCTSTR String);
	void SetLength(short Length);
	void Overlay(short offset, LPCTSTR String);
	void SetData(const VARIANT& Data);
	VARIANT Data();
};
