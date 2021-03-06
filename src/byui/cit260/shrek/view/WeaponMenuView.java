/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package byui.cit260.shrek.view;

/**
 *
 * @author bruno
 */
//import byui.cit260.shrek.control.WeaponControl;
import byui.cit260.shrek.control.WeaponControl;
import byui.cit260.shrek.exceptions.WeaponControlException;
import byui.cit260.shrek.model.InventoryItem;
import shrek.Shrek;
//import byui.cit260.shrek.control.WallControl;
//import byui.cit260.shrek.exceptions.PathwayControlException;

/**
 *
 * @author bruno
 */
public class WeaponMenuView extends View{
   InventoryItem[] inventory = Shrek.getCurrentGame().getInventory(); 
   
    public WeaponMenuView(){
    //private final String MENU="\n"
      
        super("\n"
    
    //public class WallMenuView {

    //  private final String MENU="\n"
            +"\n----------------------------------------"
            +"\n|         Princess rescueing adventure      |"
            +"\n----------------------------------------"
            +"\nThe goal of this adventure is to rescue the princess climbing to the tower"
            +"\nSo you have to choose the slope and the speed of the weapon to go over the tower. "
            + "\nThe number of trying is your spears number ");
            
    }
    @Override
    public void display() {
       // InventoryItem[] inventory = Shrek.getCurrentGame().getInventory();
    
    int numberItem=0;
     for (InventoryItem myItem:inventory) {
            if (myItem.getInventoryType()=="Arrow") {
                numberItem=myItem.getQuantityInStock();
        this.console.println("Your number of arrows is "+numberItem);
       if (numberItem>0){   
        int numberIteration=0;
       
        double slope=0.0;
        double speed=0.0;
        boolean repeatDisplay=false;
        
        //System.out.println(MENU);
        this.console.println(super.getPromptMessage());
        do {  
            numberIteration++;
        
            boolean value1=true;
            boolean value2=true;
          
            while(value1==true) {
            this.console.println("Insert the slope within O to 90 degrees or enter E to Exit");
            String value = this.getInput();
            if (value.equals("E")||value.equals("e"))return;
            
             try{
                slope = Double.parseDouble(value);
                value1=false;
             }catch(NumberFormatException nf) {
                ErrorView.display(this.getClass().getName(),"Enter a valid number.Try again or enter E to Exit");}
             catch (Throwable te){
                ErrorView.display(this.getClass().getName(),te.getMessage());
                te.printStackTrace();
             }
            }
            //System.out.println(MENUslope);
            //slope = Double.valueOf(this.getInput());
            while(value2==true) {
              this.console.println("Insert the speed within 1 m/s and 20 m/s or enter E to Exit");
              String value = this.getInput();
              if (value.equals("E")||value.equals("e"))return;
              try{
                    speed = Double.parseDouble(value);
                    value2=false;
                }catch(NumberFormatException nf) {
                 ErrorView.display(this.getClass().getName(),"Enter a valid number.Try again or enter E to Exit");}
                catch (Throwable te){
                ErrorView.display(this.getClass().getName(),te.getMessage());
                te.printStackTrace();
                }
            } 
            repeatDisplay=this.doAction(slope,speed);
            myItem.setQuantityInStock(numberItem-1);
         numberItem--;
        } while(repeatDisplay==true && numberIteration<=numberItem);
     }else {System.out.println("Please purchase an arrow");}
   }     
  }
  }   
    @Override
    public boolean doAction(Object obj) {return false;}
    
    public boolean doAction(Object obj, Object obj2) {
    double mySlope=(double)obj;
    double mySpeed=(double)obj2;
    boolean repeat=true;    
      WeaponControl myWeaponControl = new WeaponControl();
      double myHeight=0.0;
       try {
       myHeight = myWeaponControl.calcLaunchHeight(mySpeed, mySlope);
       } catch(WeaponControlException wce) {
               ErrorView.display(this.getClass().getName(),wce.getMessage());
       }
       myHeight=Math.round(myHeight*100);
       myHeight=myHeight/100;
       this.console.println("The height is: "+myHeight);
       if (myHeight>10)
        {this.console.println("\nYou climbed the tower!!");
           Shrek.getCurrentGame().setWinWeapon(true);
        if(Shrek.getCurrentGame().isWinWall()&&Shrek.getCurrentGame().isWinPathway())
         {this.console.println("\nYou win the game!!");
         repeat=false;
        // int a =0;
         }   
        repeat=false;
        }
        else {this.console.println("\nRetry values because the height must be > 10m !!");}
    return repeat;
    }
}

