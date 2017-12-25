/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classgroup;

/**
 *
 * @author mahabir pun
 */
public class CountCharacter {

    public int countCharacter(String words){
        char [] arr = words.toCharArray();
        int count = 0;
        for(int i=0;i<arr.length;i++){
            count++;
        }
        return count;
    }

}
