// https://excalidraw.com/#json=jc-KKvmvvped7vH9XhcLx,AQY4M6p20HeD7g63gD1hmA
package DocumentEditor;
import java.util.ArrayList;
import java.util.List;

interface DocumentElement{
    String render();
}

class TextElement implements DocumentElement{
    private String text;

    public TextElement(String text){
        this.text=text;
    }

    @Override
    public String render(){
        return this.text;
    }
}

class ImageElement implements DocumentElement{
    private String path;

    public ImageElement(String path){
        this.path=path;
    }

    @Override
    public String render(){
        return "Image"+this.path;
    }
}


class Document{
    private List<DocumentElement> documentElements=new ArrayList<>();

    public void addElement(DocumentElement element) {
        documentElements.add(element);
    }


    public String render() {
        StringBuilder result = new StringBuilder();
        for (DocumentElement element : documentElements) {
            result.append(element.render());
        }
        return result.toString();
    }
}

interface Persistence{
    void save(String data);
}

class FileStorage implements Persistence{

    @Override
    public void save(String data){
        // mock file creation
        System.out.println("Document saved to document.txt");
    }
}

class DBStorage implements Persistence{

    @Override
    public void save(String data){
        // mock save to DB
        System.out.println("Document saved to DB Connection");
    }
}


class DocumentEditor{
    private Document document;
    private Persistence storage;
    private String renderedDocument="";
    
    
    public DocumentEditor(Document document, Persistence storage){
        this.document=document;
        this.storage=storage;
    }

    public void addText(String text){
        document.addElement(new TextElement(text));
    }

    public String renderDocument(){
        renderedDocument=document.render();
        return renderedDocument;
    }

    public void saveDocument(){
        storage.save(renderDocument());
    }

}
//client
public class DocumentEditorLLD {
    public static void main(String[] args) {
        Document document = new Document();
        Persistence persistence = new FileStorage();

        DocumentEditor editor = new DocumentEditor(document, persistence);

        // Simulate a client using the editor with common text formatting features.
        editor.addText("Hello, world!");

        // Render and display the final document.
        System.out.println(editor.renderDocument());

        editor.saveDocument();
    }
}
