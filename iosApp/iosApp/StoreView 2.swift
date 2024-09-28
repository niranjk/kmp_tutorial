

import Foundation
import SwiftUI
import shared

struct StoreView : View {
    let mainViewModel: MainViewModel

    // The ViewModel exposes a StateFlow.
    // We collect() the StateFlow into State, which can be used in SwiftUI.
    @State
    var storeUIState: StoreUiState = StoreUiState(storeDetails: [])

    @State
    private var expanded = false

    var body: some View {
        VStack {
            HStack {
                let total = storeUIState.storeDetails.reduce(0) { $0 + ($1.count) }
                Text("Store has \(total) items").padding()
                Spacer()
                Button {
                    expanded.toggle()
                } label: {
                    if (expanded) {
                        Text("collapse")
                    } else {
                        Text("expand")
                    }
                }.padding()
            }
            if (expanded) {
                StoreDetailsView(mainViewModel: mainViewModel)
            }
        }
        .collect(flow: self.mainViewModel.storeUiState, into: $storeUIState)
    }
}

struct StoreDetailsView: View {
    let mainViewModel: MainViewModel

    // The ViewModel exposes a StateFlow.
    // We collect() the StateFlow into State, which can be used in SwiftUI.
    @State
    var storeUIState: StoreUiState = StoreUiState(storeDetails: [])

    var body: some View {
        VStack {
            ForEach(storeUIState.storeDetails, id: \.car.id) { item in
                Text("\(item.car.name): \(item.count)")
            }
        }
        .collect(flow: mainViewModel.storeUiState, into: $storeUIState)
    }
}
