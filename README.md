Simple Image Batch Resizer
============

This is a simple application written in Java to resize the images.  I wrote it as an out-of-hours side project to assist a friend - who was restricted to using - and colleague and sadly rushed it into production, so I plan to use this repository to evolve it into a much more cohesive, modular application with a keen view to reusability.

A copy of the distributable (JAR) is [available for download here](dist/ImageResizer.jar).

The application scans through a selection of supported files within a given source directory, with the following limitations at present:
 - This scan is non-recursive; any subdirectories of the given source (and subdirectories thereof, etc etc) are ignored;
 - The only supported image type is JPEG;
 - Supported files are identified by image extension rather than MIME type.

End-users are presented by a UI which allows them to configure:
 - The source directory, within which the candidate image files;
 - A directory to which resized images shall be saved (retaining the same file name);
 - A directory to which images that could not be resized - for whatever reason - shall be copied to (this was purpose specific; future releases will split this out into an optional feature);
 - A maximum image size in pixels;
 - A minimum image size in pixels;
 - A number of images that can be processed simultaneously (in non-layman's terms, the number of threads allocated to a thread pool that is responsible for handling the submission and running of image resize tasks);

The interface also provides a log which provides a good amount of useful information.

The following rules are observed:
 - Neither the save-to directory nor the on-fail-copy-to directory may be the same as the source;
 - No more than 10 images can be processed concurrently (this was to accommodate the known spec of my colleague's machine);
 - Every image is always squared to ensure that the width and height of the resized image are equal against a white background/canvas;
 - If the largest dimensional component (width or height) is greater than the given maximum size, the difference is used to produce a multiplier by which the image should be scaled down.
 - If any dimensional components are smaller than the given minimum size, a white background/canvas will surround the difference (with the image in the centre).

Please note that this project was constructed within - and built by - **NetBeans IDE** using **JDK1.7.0u13**.
