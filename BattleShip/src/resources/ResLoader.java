package resources;

import java.net.URL;

/*
 *  Created By Sehoon Min.
 *  Modified Date : 2016-01-21
 * 
 *  -- Usage --
 *  
 *  function ( url )
 * 
 *  -> function ( ResourceLoader.getResURL("filename"));
 *  ex ) function ( ResourceLoader.getResURL("1.jpg"));
 *      function ( ResourceLoader.getResURL("graphics/ship1.png"));
 *  
 */

public class ResLoader {
   public static URL getResURL(String fileName)
   {
      
      URL location = null;
      try{
         location = ResLoader.class.getResource(fileName);
      }catch(NullPointerException e)
      {
         System.out.println(e.getMessage());
      }
 
      return location;
   }
}