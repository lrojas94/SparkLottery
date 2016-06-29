
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h1><i class="fa fa-trophy"></i> Ganador! <br>
                <small>
                    Escribe un comentario y sube una foto para que todos sepan que ganaste
                </small>
            </h1><!-- Title -->
        </div>
        <div class="col-xs-12">
            <form action="/winner/upload" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="comment"><i class="fa fa-comment"></i> Comentario</label>
                    <input id="postComment" name="comment" type="text" class="form-control col-xs-12 col-md-12">
                </div>
                <div class="form-group">
                    <label for="uploaded_file"><i class="fa fa-picture-o"></i> Imagen</label>
                    <input id="uploadBtn" name="uploaded_file" type="file" accept="image/*" class="form-control file-loading">
                </div>
                <button type="submit" class="btn btn-primary col-xs-12 col-md-12">Enviar</button>
            </form>
        </div>
    </div>
</div>
