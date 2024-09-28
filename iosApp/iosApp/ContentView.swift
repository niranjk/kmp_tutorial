import SwiftUI
import shared
import Foundation

struct ContentView: View {
    var mainViewModel: MainViewModel

    // The ViewModel exposes a StateFlow.
    // We collect() the StateFlow into State, which can be used in SwiftUI.
    // https://skie.touchlab.co/features/flows-in-swiftui
    @State
    var homeUIState: HomeUiState = HomeUiState(carList: [])

    var body: some View {
        Text("Car").font(.largeTitle).fontWeight(.bold)
        StoreView(mainViewModel: mainViewModel)
        ScrollView {
            LazyVStack {
                ForEach(homeUIState.carList, id: \.self) { value in
                    CarView(car: value, addToStore: { car in
                        Task {
                            self.mainViewModel.addItemToStore(car: car)
                        }
                    })
                }
            }
            // https://skie.touchlab.co/features/flows-in-swiftui
            .collect(flow: self.mainViewModel.homeUiState, into: $homeUIState)
        }
    }
}

struct CarView: View {
    var car: Car_
    var addToStore: (Car_) -> Void
    var body: some View {
        HStack(alignment: .firstTextBaseline) {
            ZStack {
                RoundedRectangle(cornerRadius: 15).fill(Color(red: 0.8, green: 0.8, blue: 1.0))
                VStack {
                    Text("\(car.name)")
                        .fontWeight(.bold)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    Text("\(car.brand)")
                        .frame(maxWidth: .infinity, alignment: .leading)
                }.padding()
                Spacer()
                Button(action: { addToStore(car) }, label: {
                    Text("Add")
                }).padding().frame(maxWidth: .infinity, alignment: .trailing)
            }.padding([.leading, .trailing])
        }
    }
}
