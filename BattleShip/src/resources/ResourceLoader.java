package resources;

import java.net.URL;


/*
 *  -- Usage --
 *  
 *  function ( url you need )
 * 
 *  -> function ( ResourceLoader.getResURL("filename"));
 *  ex ) function ( ResourceLoader.getResURL("1.jpg"));
 *      function ( ResourceLoader.getResURL("graphics/ship1.png"));
 *  
 */

public class ResourceLoader {
   
   static ResourceLoader inst ;
   
   public static URL getResURL(String fileName)
   {
      if(inst == null)
         inst = new ResourceLoader();
      
      URL location = null;
      try{
         location = ResourceLoader.class.getResource(fileName);
      }catch(NullPointerException e)
      {
         System.out.println(e.getMessage());
      }
 
      return location;
   }
}