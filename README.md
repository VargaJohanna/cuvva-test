## Cuvva Technical Test

## Solution
I'm following the **MVVM** architecture pattern. As a result, the application is divided into three layers: view, view model and repositories.


Each view is a single activity and represented by fragments.


The navigation between fragments is managed by the **Navigation Component**.
Below the view layer, I'm using **RxJava** to asynchronously retrieve the data from the network when the app is launched and save it to the local database via **Room**. I subscribe to the saved data in each **ViewModel** of the relevant Fragment and transfer it into lifecycle aware data (LiveData).
The fragment will observe these LiveDatas and react to the changes.


Every screen can be refreshed by swiping down. This will trigger the *fetchData()* method that will delete the existing data in the database and updated it with the latest response from the network.


All the exceptions are displayed as Toast messages.


### Data structure:
In the *model* package I created the data objects I needed for filling the UI in the easiest way. 
To achieve this I decided to map the network response in a way where each vehicle contains a list of all the created policies it's associated with.
**VehicleAndPolicies** data object serves this purpose where I also added boolean to know if the vehicle is active or not.
The value is decided in the [VehicleAndAllCreatedPolicyEntities](https://github.com/VargaJohanna/cuvva-test/blob/master/app/src/main/java/com/android/cuvvatest/repositories/vehicle/VehicleAndAllCreatedPolicyEntities.kt) class at the extension function.
The remaining time of the active policy is also set at the same class. This is all based on the assumption that a vehicle can have only one active policy.


The [**CreatedPolicy**](https://github.com/VargaJohanna/cuvva-test/blob/master/app/src/main/java/com/android/cuvvatest/model/CreatedPolicy.kt) data object has an **extensionPolicy** boolean field.
This value is set based on the result of the comparison of the policyId and the originalPolicyId.
I'm [using](https://github.com/VargaJohanna/cuvva-test/blob/master/app/src/main/java/com/android/cuvvatest/ui/home/VehicleAdapter.kt) this value when I count the number of policies associated with a vehicle and when I'm displaying the previous policies on the [second screen.](https://github.com/VargaJohanna/cuvva-test/blob/master/app/src/main/java/com/android/cuvvatest/ui/vehicle/VehicleViewModel.kt)
The CreatedPolicy object also has an **active** field. This boolean is set based on the end and start date of the policy.
The calculations is done when the data is saved to the database [here](https://github.com/VargaJohanna/cuvva-test/blob/master/app/src/main/java/com/android/cuvvatest/repositories/policies/created/CreatedPolicyEntity.kt).


Please note that in order to be able to test the app I used a date in the past as current date. It's set in [Constants](https://github.com/VargaJohanna/cuvva-test/blob/master/app/src/main/java/com/android/cuvvatest/Constants.kt) file.
To test is with live data please change it to **LocalDateTime.now()**


To make easier to know if a CreatedPolicy is cancelled or not I created the **Policy** data object.
It has a boolean which is set based on a search in the **cancelledPolicyDao** [here](https://github.com/VargaJohanna/cuvva-test/blob/master/app/src/main/java/com/android/cuvvatest/repositories/policies/PolicyRepositoryImpl.kt).


Please note that I'm using a custom *GsonConverter* to be able to map the response in the most sufficient way I could find at the moment. It's the **PolicyDeserializer** class.


### Used libraries:
* Koin
* Retrofit
* RxJava
* Junit
* Mockito,
* Room
* JST-310, Threetenabp for managing date and time


I provided a sample unit test suite as well.

### Things I would change
* In the designs every date is displayed with the appropriate *th* suffix. At the moment it's not displayed but it could be achieved by
a global Date formatter that would sort out this suffix.
* On the first and second screen I would use a collapsible app bar that would shrink and reorganize its values on scroll up.
* On the second screen, the previous driving policy duration should be displayed in a readable way. Days, hours and minutes, depending on the duration.
* The design for a vehicle without an active policy should be revisited by a designer, as I'm simply displaying a message and changing the text of the button to make it reasonable.
* Unfortunately I did not have the time for the optional task, but if I had then I would've followed a very similar structure as with the data stream. There wouldn't be a need for a custom converter, and the data objects would feed the relevant value for the TextViews.

### How long it took
I spent roughly 30 hours to finish this task.
