package cz.xelfi.test.client;

import net.java.html.boot.BrowserBuilder;
import org.netbeans.api.nbrwsr.OpenHTMLRegistration;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;

public final class Main {
    private Main() {
    }
    
    public static void main(String... args) throws Exception {
        BrowserBuilder.newBrowser().
            loadPage("pages/index.html").
            loadClass(Main.class).
            invoke("onPageLoad", args).
            showAndWait();
        System.exit(0);
    }
    //
    // the following annotations generate registration for NetBeans,
    // they are harmless in other packaging schemes
    //
    
    @ActionID(
        category = "Games",
        id = "cz.xelfi.test.client.OpenPage"
    )
    @OpenHTMLRegistration(
        url="index.html",
        displayName = "Open Your Page",
        iconBase = "cz/xelfi/test/client/icon.png"
    )
    @ActionReferences({
        @ActionReference(path = "Menu/Window"),
        @ActionReference(path = "Toolbars/Games")
    })
    //
    // end of NetBeans actions registration
    //

    /**
     * Called when the page is ready.
     */
    public static void onPageLoad() throws Exception {
        Data d = new Data();
        d.setMessage("Hello World from HTML and Java!");
        d.applyBindings();
    }
    
}
