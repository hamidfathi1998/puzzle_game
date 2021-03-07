package ir.hfathi.puzzelgame;

import java.util.ArrayList;

/**
 * Created by hamid on 27/04/2019.
 */
public class AlModel {

    private int[][] number;
    private String solution="";
    private ArrayList<AlModel> childs;

    AlModel(int[][] number, String solution) {
        this.number = number;
        this.solution += solution;
    }

    AlModel(int[][] number, String solution, ArrayList<AlModel> childs) {
        this.number = number;
        this.solution += solution;
        this.childs = childs;
    }

    public int[][] getNumber() {
        return number;
    }

    public ArrayList<AlModel> getChilds() {
        return childs;
    }

    public String getSolution() {
        return solution;
    }

    public void setChilds(ArrayList<AlModel> childs) {
        this.childs = childs;
    }

    public void setNumber(int[][] number) {
        this.number = number;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
