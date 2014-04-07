
class Pal {
  String str;

  public Pal(String s) {
    str = s;
  }

  public native boolean findPalindrome(int length);

  public void testNative() {
    boolean b= findPalindrome(str.length());
    if(b)
      System.out.println(" The string " + str + " is a palindrome");
    else
      System.out.println(" The string " + str + " is not a palindrome");
  }

  static {
    System.loadLibrary("Pal");
  }
}

