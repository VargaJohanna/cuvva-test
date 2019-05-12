## cuvva Technical Test

## Solution
I'm following the MVVM architecture pattern. As a result, the application is divided into three layers: view, view model and repositories.


Each view is a single activity and represented by fragments.


The navigation between fragments is managed by the Navigation Component.
Below the view layer, I'm using RxJava to asynchronously retrieve the data from the network. I subscribe to this data in ViewModel of the relevant Fragment and transfer it into lifecycle aware data (LiveData).
The fragment will observe these LiveDatas and react to the changes.


The data is retrieved from the network once when the user opens the app and then it's saved to local database with the help of Room library.
Every screen can be refreshed by swiping down. This will trigger the fetchData() method that will delete the exising data in the database and updated it with the latest response.


All the exceptions are displayed as Toast messages.


On every screen, it's possible to swipe the screen and refresh, so the user is able to retry in case the product details didn't come through.


Data structure:
In the "model" package I created the data object I'd need to fill the ui in the easiest way. 
To achieve it I decided to map the network response in a way where each vehicle contains a list of all the created policies it's associated with.
VehicleAndPolicies data object serves this purpose where I also added boolean to know if the vehicle is active or not.
The value is decided in the VehicleAndAllCreatedPolicyEntities class (com.android.cuvvatest.repositories.vehicle.VehicleAndAllCreatedPolicyEntities) at the extension function.
The remaning time of the active policy is also set at the same class. This is all based on the assumption that a vehicle can have only one active policy.


The CreatedPolicy data object (com.android.cuvvatest.model.CreatedPolicy) has an extensionPolicy boolean field.
This value is set based on the result of the comparison of the policyId and the originalPolicyId.
I'm using this value when I count the number of policies associated with a vehicle
(com/android/cuvvatest/ui/home/VehicleAdapter.kt:35)
and when I'm displaying the previous policies on the second screen.
The CreatedPolicy object also has an active field. This is boolean set based on the end and start date of the policy.
The calculations is done when the data is saved ot the database. (com/android/cuvvatest/repositories/policies/created/CreatedPolicyEntity.kt)


Please note that in order to be able to test the app I used a date in the past as a current date. It's set in com.android.cuvvatest.Constants file.
To test is with live data please change it to LocalDateTime.now()


To make easier to know of a CreatedPolicy is cancelled or not I created the Policy data object.
It has a boolean which is set based on a search in the cancelledPolicyDao. (com.android.cuvvatest.repositories.policies.PolicyRepositoryImpl#getPolicy)


Please note that I'm using a custom GsonConverter to be able to map the response in the most sufficient way I could find at the moment. It's the PolicyDeserializer class.


Used libraries:
* Koin
* Retrofit
* RxJava
* Junit
* Mockito,
* Room
* JST-310, Threetenabp for managing date and time


I provided a sample unit test suite as well.
