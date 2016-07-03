package com.example.pc002.mazetest1;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maza {
    private int[][] grid;
    private int numberRows;
    private int numberColumns;

    public Maza(String filePath) {
        try {
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(new File(filePath));
            numberRows = scanner.nextInt();
            numberColumns = scanner.nextInt();

            grid = new int[numberRows][numberColumns];
            for(int i=0; i<numberRows; ++i)
                for(int j=0; j<numberColumns; ++j){
                    grid[i][j] = scanner.nextInt();
                }
        } catch (FileNotFoundException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    public Maza(int[][] grid) {
        this.grid = grid;
        numberRows = grid.length;
        numberColumns = grid[0].length;

        System.out.println("rows: " + numberRows + "cols" + numberColumns);
    }

    public boolean isValidPath(int row, int col){
//		System.out.println("row: " + row + " column: " + col);
        if(row < numberRows && row >= 0 && col < numberColumns && col >= 0)
            return grid[row][col] == 1;
        return false;
    }

    public void tryPath(int row, int col){
        grid[row][col] = 2;
    }

    public void backPath(int row, int col){
        grid[row][col] = 1;
    }

    public int getRows(){
        return numberRows;
    }
    public int getColumns(){
        return numberColumns;
    }

    public void setMazaValue(int row, int col, int value){
        grid[row][col] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<numberRows; ++i){
            for(int j=0; j<numberColumns; ++j){
                sb.append(Integer.toString(grid[i][j])).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
