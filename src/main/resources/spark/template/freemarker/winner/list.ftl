<!-- FOREACH WINNER IN WINNERS -->
<#import "../macros/winners.ftl" as Winners>
<#if winners??>
<h1 class="text-center">Conoce a nuestros Ganadores!!</h1>
<br>
<table id='winner-table' class="table table-responsive table-hover">
    <thead><th></th></thead>
    <tbody>
    </tbody>

</table>
</#if>


<div class="winner-container"  id="winner-container-template" hidden>
<div class="content-section-a">
    <div class="container">
        <div class="row">
            <div class="col-lg-5 col-sm-6">
                <hr class="section-heading-spacer">
                <div class="clearfix"></div>
                <h2 class="winner-heading"></h2>
                <p class="winner-lead"></p>
            </div>
            <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                <img class="img-responsive center-block img-circle" src="" alt="">
            </div>
        </div>
    </div>
</div>
</div>