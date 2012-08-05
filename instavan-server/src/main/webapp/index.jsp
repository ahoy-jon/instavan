<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>


        <form action="resources/photos/" method="post" enctype="multipart/form-data">

            <p>
                Front a file : 
                <input type="file" name="frontfile"  />
            </p>

            <p>
                profilePhotoId a file : 
                <input type="file" name="profilefile"  />
            </p>

            <p>
                message :
                <textarea name="message">La carte postale est un moyen de correspondance écrite qui se présente sous la forme d'un morceau de papier cartonné rectangulaire, de dimensions variables (le format le plus courant est le format A6, soit environ 10 x 15 cm), envoyé sans enveloppe, l'adresse et l'affranchissement y étant porté directement, aux côtés du message.La carte postale est un moyen de correspondance écrite qui se présente sous la forme d'un morceau de papier cartonné rectangulaire, de dimensions variables (le format le plus courant est le format A6, soit environ 10 x 15 cm), envoyé sans enveloppe, l'adresse et l'affranchissement y étant porté directement, aux côtés du message.</textarea>
            </p>


            <fieldset>
                <h3>sender</h3>
                <p>
                    name : 
                    <input type="text" name="sender_name" value="Martin DIDOHU" />
                </p>
                <p>
                    sender_email : 
                    <input type="text" name="sender_email" value="Martin.DIDOHU@gmail.com"  />
                </p>
                <p>
                    sender_company : 
                    <input type="text" name="sender_company" value="Company Campus"  />
                </p>
                <p>
                    sender_street1 : 
                    <input type="text" name="sender_street1"  value="17 rue de la tour d'auvergne" />
                </p>
                <p>
                    sender_street2 : 
                    <input type="text" name="sender_street2"  value="immeuble ile rouge" />
                </p>
                <p>
                    sender_city : 
                    <input type="text" name="sender_city"   value="Nantes" />
                </p>
                <p>
                    sender_state : 
                    <input type="text" name="sender_state"  value="PDL" />
                </p>
                <p>
                    sender_postalcode : 
                    <input type="text" name="sender_postalcode"  value="44000" />
                </p>
                <p>
                    sender_country : 
                    <input type="text" name="sender_country"  value="France" />
                </p>
            </fieldset>
            
            <fieldset>
                <h3>recipient0</h3>
                <p>
                    name : 
                    <input type="text" name="recipient_name_0" value="Martin DIDOHU" />
                </p>
                <p>
                    recipient_email : 
                    <input type="text" name="recipient_email_0" value="Martin.DIDOHU@gmail.com"  />
                </p>
                <p>
                    recipient_company : 
                    <input type="text" name="recipient_company_0" value="Company Campus"  />
                </p>
                <p>
                    recipient_street1 : 
                    <input type="text" name="recipient_street1_0"  value="17 rue de la tour d'auvergne" />
                </p>
                <p>
                    recipient_street2 : 
                    <input type="text" name="recipient_street2_0"  value="immeuble ile rouge" />
                </p>
                <p>
                    recipient_city : 
                    <input type="text" name="recipient_city_0"   value="Nantes" />
                </p>
                <p>
                    recipient_state : 
                    <input type="text" name="recipient_state_0"  value="PDL" />
                </p>
                <p>
                    recipient_postalcode : 
                    <input type="text" name="recipient_postalcode_0"  value="44000" />
                </p>
                <p>
                    recipient_country : 
                    <input type="text" name="recipient_country_0"  value="France" />
                </p>
            </fieldset>





            <input type="submit" value="Upload now !" />
        </form>
    </body>
</html>
