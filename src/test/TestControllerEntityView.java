package test;

/**
 * TestControllerEntityView.
 *
 */

public class TestControllerEntityView implements TestEntityController {
    private int j = 0;
    private String status;
    private String move;
    private String body;
    private String head; 
    /**
     * 
     * @param aux simple {@link String} test
     * @return string 
     */
        public String test(final String aux) {
            j++;
            return "call test " + aux + " " + j;
        }
    /**
     * 
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param move 
     */
    public void normal(final String move) {
        this.status = "normal";
        this.move = move;
    }

    /**
     * 
     * @return move
     */
    public String getMove() {
        return move;
    }

    /**
     * 
     * @param move 
     */
    public void setMove(final String move) {
        this.move = move;
    }

    /**
     * 
     * @return body 
     */
    public String getBody() {
        return body;
    }
    /**
     * 
     * @param body 
     */
    public void setBody(final String body) {
        this.body = body;
    }
    /**
     * 
     * @return head
     */
    public String getHead() {
        return head;
    }
    /**
     * 
     * @param head 
     */
    public void setHead(final String head) {
        this.head = head;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TestControllerEntityView [j=" + j + ", status=" + status + ", move=" + move + ", body=" + body
                + ", head=" + head + "]";
    }

}
