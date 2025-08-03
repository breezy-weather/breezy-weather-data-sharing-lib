# Breezy Weather Data Sharing Library

**THIS LIBRARY IS NOT YET READY TO BE USED**

This library allows you to re-use Breezy Weather data in your app.
Examples of things you can do:
- Your own widgets
- Show the weather in a dashboard
- Make your own design (for example, if you don’t like Material 3 Expressive)

The library contains what you need to use the Content Provider:
- Information about the content provider
- Json serialized objects

It also contains a sample app to showcase how it can be done, although it was intentionally made with a minimum logic to make it easy to understand.

<img src="docs/sample.jpg" width="300" alt="" />


# Usage

## Permission

Your `AndroidManifest.xml` must include:
```xml
<uses-permission android:name="org.breezyweather.READ_PROVIDER" />
```

If you want to support debug builds of Breezy Weather, you can also add:
```xml
<uses-permission android:name="org.breezyweather.debug.READ_PROVIDER" />
```

The user will need to manually allow the permission to access Breezy Weather data.


## Fetch data from content provider

### Version check

The first thing you should do is check if you currently have a library compatible with the version of content provider from the user installed Breezy Weather version.

Version of the content provider follows the following scheme:
- A major version indicates a breaking change, such as changed type or deleted non-null fields
- A minor version indicates added features/fields, renamed or deleted nullable fields. If you use Kotlinx Serialization Json, configured as shown in our sample, this change should not break your app. In the worst case, you will miss some nullable fields, which you should already be able to handle.

**Known limit:** This means that when a major version is out, Breezy Weather and your app must be updated at the same time. However, a part of users may update Breezy Weather or your app later than the other.

**TO BE COMPLETED**


# License

* [GNU Lesser General Public License v3.0](/LICENSE)
* This License does not grant any rights in the trademarks, service marks, or logos of any Contributor.
* Misrepresentation of the origin of that material is prohibited, and modified versions of such material must be marked in reasonable ways as different from the original version.
