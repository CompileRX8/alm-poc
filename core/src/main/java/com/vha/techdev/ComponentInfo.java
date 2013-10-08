package com.vha.techdev;

public class ComponentInfo {
    public static final class VersionInfo {
        private VersionInfo(String title, String version, String vendor) {
            TITLE = title;
            VERSION = version;
            VENDOR = vendor;
        }
        public final String TITLE;
        public final String VERSION;
        public final String VENDOR;
    }

    private static final Package pkg = ComponentInfo.class.getPackage();

    public static final VersionInfo IMPLEMENTATION = new VersionInfo(
            pkg.getImplementationTitle(),
            pkg.getImplementationVersion(),
            pkg.getImplementationVendor());

    public static final VersionInfo SPECIFICATION = new VersionInfo(
            pkg.getSpecificationTitle(),
            pkg.getSpecificationVersion(),
            pkg.getSpecificationVendor()
    );
}
