
class Ex82_RowBean {
    int id;
    String name;
    double score;

    Ex82_RowBean(int id, String name, double score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    ;

    Ex82_RowBean() {
        id = -1;
        name = "";
        score = 0d;
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id 要设置的 id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 要设置的 name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return score
     */
    public double getScore() {
        return score;
    }

    /**
     * @param score 要设置的 score
     */
    public void setScore(double score) {
        this.score = score;
    }

}
