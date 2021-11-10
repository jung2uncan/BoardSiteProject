package boardSite;

public class BoardSite {
	private int boardID;			//게시클 번호
	private String boardTitle;		//게시글 제목
	private String userID;			//작성자
	private String boardDate;		//게시글 작성일
	private String boardContent;	//게시글 내용
	private int boardAvailable;		//게시글 활성 여부
	
	public int getBoardID() {
		return boardID;
	}
	public void setBoardID(int boardID) {
		this.boardID = boardID;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardDate() {
		return boardDate;
	}
	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public int getBoardAvailable() {
		return boardAvailable;
	}
	public void setBoardAvailable(int boardAvailable) {
		this.boardAvailable = boardAvailable;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
}