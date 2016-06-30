<#macro winner_list winner> <!-- Used to list winner without index site -->
<div class="winner-container">
    <div class="content-section-a">
        <div class="container">
            <div class="row">
                <div class="col-lg-5 col-sm-6">
                    <hr class="section-heading-spacer">
                    <div class="clearfix"></div>
                    <#assign Player = winner.getPlayer()>
                    <h2 class="section-heading">${Player.getFirstName()} ${Player.getLastName()}</h2>
                    <p class="lead">${winner.getComment()}</p>
                </div>
                <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                    <img class="center-block img-responsive" src="${winner.getPath()}" alt="">
                </div>
            </div>
        </div>
    </div>
</div>
</#macro>