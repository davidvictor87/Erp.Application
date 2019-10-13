package erp.application.login.model;

public enum LevelType {
	
	ADMIN(1), MANAGER(2), USER(3);
	
	private int level;
	
	private LevelType(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}

}
