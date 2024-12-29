# Variables d'environnement
$localUser = $env:USERNAME
$host.UI.RawUI.BackgroundColor = "Black" # Couleur de fond
$host.UI.RawUI.ForegroundColor = "LightBlue" # Couleur du texte
Clear-Host # Rafraîchit l'écran pour appliquer les couleur

function Get-UserResponse {
    $response = Read-Host "Bonjour Mouhamed ! Je suis heureux de vous voir aujourd'hui. Que puis-je faire pour vous ? "
    return $response
}

# Demander une première réponse
$response = Get-UserResponse
Write-Host "Vous avez dit : '$response'. C'est parfait !" -ForegroundColor Green


if ($response -match "acheter|produit|commande|shopping") {

    $budget = Read-Host "Quel est votre budget en $ pour l'achat des produits ? "
    $days = Read-Host "Combien de jours souhaitez-vous pour la livraison ? "
    $productsInput = Read-Host "Entrez les produits que vous souhaitez acheter (separes par des virgules) "
    $products = $productsInput.Split(",")

    Write-Output "Parfait ! Vous avez un budget de $budget pour acheter les produits suivants : [ $($products -join ', ') ] et vous souhaitez une livraison sous $days jours."
    $changeResponse = Read-Host "Voulez-vous confirmer votre demande ? (oui/non)"
        if ($changeResponse -eq "oui") {
            # Exécution de la commande Maven pour le test d'achat
                cd "C:\Users\mogueye\IdeaProjects\Auto_Achat_Produit"
                $mavenCommand = "mvn clean test -Dbudget=$budget -Dproducts='$($products -join ',')' -Ddays=$days"
                Invoke-Expression $mavenCommand
        } else {
            Write-Host "D'accord, je vais vous laisser pour maintenant. N'hesitez pas a revenir si vous avez des questions."
            break
        }

} elseif ($response -match "information|conseil|aide") {
    Write-Host "Je peux vous fournir des informations sur nos produits ou vous aider à faire un choix. 😊"
    $topic = Read-Host "De quel type d'information avez-vous besoin ? (ex : caractéristiques des produits, meilleures offres, etc.)"
    Write-Host "Vous avez demandé des informations sur '$topic'. Je vais vous fournir ce que vous cherchez."

} else {
    Write-Host "Hmm, je ne suis pas sûr de comprendre exactement ce que vous souhaitez. 😕"
    Write-Host "Si vous voulez acheter des produits, dites-le moi clairement, ou si vous avez besoin d'autres informations, je suis là pour ça !"
}
