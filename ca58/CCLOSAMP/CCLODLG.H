//---------------------------------------------------------------------------
// CcloDlg.h : header file
//---------------------------------------------------------------------------

//---------------------------------------------------------------------------
// CcloSampDlg dialog
//---------------------------------------------------------------------------
class CcloSampDlg : public CDialog
{
// Construction
public:
	CcloSampDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CcloSampDlg)
	enum { IDD = IDD_CCLOSAMP_DIALOG };
		// NOTE: the ClassWizard will add data members here
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CcloSampDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CcloSampDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};
