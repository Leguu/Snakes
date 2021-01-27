/**
 * Asil Erturan (40164714) and Christian Jerjian (40031909)
 * COMP249
 * Assignment #1
 * 2020-02-05
 */

public class Player {
    private int position;
    private String name;
    private int order;
    
    public void setPosition(int position) {
    	this.position = position;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public void setOrder(int order) {
    	this.order = order;
    }
    
    public int getPosition() {
    	return position;
    }
    public String getName() {
    	return name;
    }
    public int getOrder() {
    	return order;
    }
    

    public Player(int position, String name) {
        this.position = position;
        this.name = name;
    }
}
