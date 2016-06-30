
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h1><i class="fa fa-trophy"></i> GANASTE! <br>
                <small>
                    Sube una foto y deja un comentario para que todos te conozcan.
                </small>
            </h1><!-- Title -->
        </div>
        <div class="col-xs-12">
            <div id="kv-avatar-errors-1" class="center-block" style="width:800px;display:none"></div>
            <form action="/winner/upload" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="uploaded_file"><i class="fa fa-picture-o"></i> Imagen</label>
                    <div class="kv-avatar center-block text-center" style="width:200px">
                        <input id="uploaded_file" name="uploaded_file" type="file" accept="image/*" class="form-control file-loading">
                    </div>
                </div>
                <div class="form-group">
                    <label for="comment"><i class="fa fa-comment"></i> Comentario</label>
                    <input id="comment" name="comment" type="text" class="form-control col-xs-12 col-md-12" required>
                </div>
                <button type="submit" class="btn btn-primary col-xs-12 col-md-12" style="margin-top: 12px; margin-bottom: 12px">Enviar</button>
            </form>
        </div>
    </div>
</div>
