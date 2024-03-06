## Micronaut 4.3.4 file upload issue

`curl -vF 'file=@./image1.jpeg' -F 'file=@./image2.jpeg' http://localhost:8080/uploads`

{"_links":{"self":[{"href":"/uploads","templated":false}]},"_embedded":{"errors":[{"message":"Internal Server Error: refCnt: 0, decrement: 1"}]},"message":"Internal Server Error"}%

`curl -vF 'file=@./image1.jpeg' -F 'file=@./image2.jpeg' -F 'metadata=@./metadata.json' http://localhost:8080/uploads`

{"_links":{"self":[{"href":"/uploads","templated":false}]},"_embedded":{"errors":[{"message":"Internal Server Error: The mapper [io.micronaut.http.server.netty.binders.NettyPartUploadAnnotationBinder$$Lambda/0x00000008015b9fd0] returned a null value."}]},"message":"Internal Server Error"}%   
