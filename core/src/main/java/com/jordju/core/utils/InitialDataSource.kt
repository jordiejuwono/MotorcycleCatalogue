package com.jordju.core.utils

import com.jordju.core.data.local.room.entity.MotorcycleEntity

object InitialDataSource {
    fun getMotorcycleList(): List<MotorcycleEntity> {
        return listOf(
            MotorcycleEntity(
                motorcycleId = 0,
                motorcycleName = "Honda Beat",
                motorcycleImage = "https://imgcdn.oto.com/medium/gallery/exterior/73/2270/honda-beat-esp-29945.jpg",
                imageList = listOf(
                    "https://imgcdn.oto.com/medium/gallery/exterior/73/2270/honda-beat-esp-29945.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/2270/honda-beat-esp-front-view-full-image-602418.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/2270/honda-beat-esp-rear-viewfull-image-803682.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/2270/honda-beat-esp-engine-view-982692.jpg"
                ),
                description = "The Honda Beat 2023 price in the Indonesia starts between Rp 17,72 - 18,57 Million. it is available in 10 colors, 3 variants in the Indonesia. The Beat is powered by a 110 cc engine, and has a Variable Speed gearbox. The Honda Beat has a seating height of 740 mm and kerb weight of 90 kg. The Beat comes with Disc front brakes and Drum rear brakes. Beat top competitors are Freego Connected, Beat Street, Vario 125 and Scoopy.",
                displacement = 110,
                category = "Scooter",
                oilTankCapacity = 0.65,
                maximumPower = 8.89,
                price = 18850000.0,
                isFavorite = false,
            ),
            MotorcycleEntity(
                motorcycleId = 1,
                motorcycleName = "Honda Scoopy",
                motorcycleImage = "https://imgcdn.oto.com/medium/gallery/exterior/73/985/honda-scoopy-esp-right-side-viewfull-image-955742.jpg",
                imageList = listOf(
                    "https://imgcdn.oto.com/medium/gallery/exterior/73/985/honda-scoopy-esp-right-side-viewfull-image-955742.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/985/honda-scoopy-esp-front-view-full-image-542647.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/985/honda-scoopy-esp-rear-viewfull-image-147204.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/985/honda-scoopy-esp-engine-view-287391.jpg"
                ),
                description = "The Honda Scoopy 2023 price in the Indonesia starts between Rp 21,55 - 22,32 Million. it is available in 8 colors, 4 variants in the Indonesia. The Scoopy is powered by a 109.5 cc engine, and has a Variable Speed gearbox. The Honda Scoopy has a seating height of 746 mm and kerb weight of 94 kg. The Scoopy comes with Disc front brakes and Drum rear brakes. Scoopy top competitors are Freego Connected, Fazzio, Vario 125 and Beat.",
                displacement = 109,
                category = "Scooter",
                oilTankCapacity = 0.65,
                maximumPower = 9.0,
                price = 22680000.0,
                isFavorite = false,
            ),
            MotorcycleEntity(
                motorcycleId = 2,
                motorcycleName = "Honda Vario 125",
                motorcycleImage = "https://imgcdn.oto.com/medium/gallery/exterior/73/987/honda-vario-125-esp-slant-rear-view-full-image-260783.jpg",
                imageList = listOf(
                    "https://imgcdn.oto.com/medium/gallery/exterior/73/987/honda-vario-125-esp-slant-rear-view-full-image-260783.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/987/honda-vario-125-esp-handle-bar-view-882088.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/987/honda-vario-125-esp-head-light-view-819985.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/987/honda-vario-125-esp-front-brake-310806.jpg"
                ),
                description = "The Honda Vario 125 2023 price in the Indonesia starts between Rp 22,35 - 24,25 Million. it is available in 5 colors, 3 variants in the Indonesia. The Vario 125 is powered by a 124.8 cc engine, and has a Variable Speed gearbox. The Honda Vario 125 has a seating height of 769 mm and kerb weight of 112 kg. The Vario 125 comes with Disc front brakes and Drum rear brakes. Vario 125 top competitors are Freego Connected, Vario 160, Scoopy and Beat.",
                displacement = 125,
                category = "Scooter",
                maximumPower = 11.1,
                oilTankCapacity = 0.8,
                price = 24400000.0,
                isFavorite = false,
            ),
            MotorcycleEntity(
                motorcycleId = 3,
                motorcycleName = "Yamaha Vixion",
                motorcycleImage = "https://imgcdn.oto.com/medium/gallery/exterior/84/1083/yamaha-v-ixion-slant-rear-view-full-image-133163.jpg",
                imageList = listOf(
                    "https://imgcdn.oto.com/medium/gallery/exterior/84/1083/yamaha-v-ixion-slant-rear-view-full-image-133163.jpg",
                    "https://imgcdn.oto.com/large/gallery/color/84/1083/yamaha-v-ixion-color-781277.jpg",
                    "https://imgcdn.oto.com/large/gallery/color/84/1083/yamaha-v-ixion-color-493014.jpg",
                    "https://imgcdn.oto.com/large/gallery/color/84/1083/yamaha-v-ixion-color-921735.jpg"
                ),
                description = "The Yamaha Vixion 2023 price in the Indonesia starts between Rp 27,94 - 28,65 Million. it is available in 3 colors, 2 variants in the Indonesia. The Vixion is powered by a 149.8 cc engine, and has a 5-Speed gearbox. The Vixion comes with Disc front brakes and Disc rear brakes. Vixion top competitors are CBR250RR, MX King, CB150R Streetfire and CBR150R.",
                displacement = 150,
                maximumPower = 16.36,
                category = "Street",
                oilTankCapacity = 1.15,
                price = 29280000.0,
                isFavorite = false,
            ),
            MotorcycleEntity(
                motorcycleId = 4,
                motorcycleName = "Honda Supra X 125 FI",
                motorcycleImage = "https://imgcdn.oto.com/large/gallery/exterior/73/980/honda-supra-x-125-fi-slant-front-view-full-image-798496.jpg",
                imageList = listOf(
                    "https://imgcdn.oto.com/large/gallery/exterior/73/980/honda-supra-x-125-fi-slant-front-view-full-image-798496.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/980/honda-supra-x-125-fi-front-view-full-image-865327.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/980/honda-supra-x-125-fi-rear-viewfull-image-961529.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/980/honda-supra-x-125-fi-engine-view-690976.jpg"
                ),
                description = "The Honda Supra X 125 FI 2023 price in the Indonesia starts between Rp 18,78 - 19,92 Million. it is available in 2 colors, 2 variants in the Indonesia. The Supra X 125 FI is powered by a 124.89 cc engine, and has a 4-Speed gearbox. The Supra X 125 FI comes with Disc front brakes and Disc rear brakes. Supra X 125 FI top competitors are ST125 Dax, Revo, Jupiter Z1 and Beat.",
                displacement = 125,
                category = "Moped",
                maximumPower = 9.92,
                oilTankCapacity = 0.7,
                price = 20220000.0,
                isFavorite = false,
            ),
            MotorcycleEntity(
                motorcycleId = 5,
                motorcycleName = "Benelli Zafferano 250",
                motorcycleImage = "https://imgcdn.oto.com/medium/gallery/exterior/66/925/benelli-zafferano-250-right-side-viewfull-image-338393.jpg",
                imageList = listOf(
                    "https://imgcdn.oto.com/medium/gallery/exterior/66/925/benelli-zafferano-250-right-side-viewfull-image-338393.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/66/925/benelli-zafferano-250-speedometer-580119.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/66/925/benelli-zafferano-250-marketing-image-162969.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/66/925/benelli-zafferano-250-right-side-viewfull-image-338393.jpg"
                ),
                description = "The Benelli Zafferano 250 2023 price in the Indonesia starts from Rp 47,5 Million. it is available in 2 colors, 1 variants in the Indonesia. The Zafferano 250 is powered by a 249.7 cc engine, and has a Variable Speed gearbox. The Zafferano 250 comes with Dual Disc front brakes and Disc rear brakes. Zafferano 250 top competitors are Freego Connected, Joyride 200i, Medley and Django 150.",
                displacement = 250,
                category = "Scooter",
                maximumPower = 20.78,
                oilTankCapacity = 0.0,
                price = 47500000.0,
                isFavorite = false,
            ),
            MotorcycleEntity(
                motorcycleId = 6,
                motorcycleName = "Honda PCX160",
                motorcycleImage = "https://imgcdn.oto.com/medium/gallery/exterior/73/1895/honda-pcx-right-side-viewfull-image-882141.jpg",
                imageList = listOf(
                    "https://imgcdn.oto.com/medium/gallery/exterior/73/1895/honda-pcx-right-side-viewfull-image-882141.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/1895/honda-pcx-slant-front-view-full-image-842067.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/1895/honda-pcx-front-view-full-image-485239.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/1895/honda-pcx-left-side-view-full-image-960057.jpg"
                ),
                description = "The Honda PCX160 2023 price in the Indonesia starts between Rp 32,08 - 35,51 Million. it is available in 6 colors, 2 variants in the Indonesia. The PCX160 is powered by a 156.9 cc engine, and has a Variable Speed gearbox. The Honda PCX160 has a seating height of 764 mm and kerb weight of 132 kg. The PCX160 comes with Disc front brakes and Disc rear brakes along with ABS. PCX160 top competitors are Freego Connected, Vario 160, Nmax Connected and Forza 250.",
                displacement = 157,
                category = "Scooter",
                maximumPower = 15.8,
                oilTankCapacity = 0.8,
                price = 32620000.0,
                isFavorite = false,
            ),
            MotorcycleEntity(
                motorcycleId = 7,
                motorcycleName = "Honda CRF150L",
                motorcycleImage = "https://imgcdn.oto.com/medium/gallery/exterior/73/1263/honda-crf-150l-right-side-viewfull-image-966547.jpg",
                imageList = listOf(
                    "https://imgcdn.oto.com/medium/gallery/exterior/73/1263/honda-crf-150l-right-side-viewfull-image-966547.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/1263/honda-crf-150l-front-view-full-image-620114.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/1263/honda-crf-150l-left-side-view-full-image-684855.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/1263/honda-crf-150l-engine-view-830897.jpg"
                ),
                description = "The Honda CRF150L 2023 price in the Indonesia starts from Rp 35,38 Million. it is available in 3 colors, 1 variants in the Indonesia. The CRF150L is powered by a 149.15 cc engine, and has a 5-Speed gearbox. The Honda CRF150L has a seating height of 869 mm and kerb weight of 122 kg. The CRF150L comes with Disc front brakes and Disc rear brakes. CRF150L top competitors are Stockman, D-Tracker, WR155 R and KLX 150.",
                displacement = 149,
                category = "Off Road",
                maximumPower = 13.0,
                oilTankCapacity = 1.0,
                price = 35730000.0,
                isFavorite = false,
            ),
            MotorcycleEntity(
                motorcycleId = 8,
                motorcycleName = "Honda Vario 160",
                motorcycleImage = "https://imgcdn.oto.com/medium/gallery/exterior/73/2569/honda-vario-160-slant-front-view-full-image-809796.jpg",
                imageList = listOf(
                    "https://imgcdn.oto.com/medium/gallery/exterior/73/2569/honda-vario-160-slant-front-view-full-image-809796.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/2569/honda-vario-160-engine-view-247752.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/2569/honda-vario-160-head-light-view-115810.jpg",
                    "https://imgcdn.oto.com/large/gallery/exterior/73/2569/honda-vario-160-exhaust-view-210207.jpg"
                ),
                description = "The Honda Vario 160 2023 price in the Indonesia starts between Rp 26,34 - 29,06 Million. it is available in 4 colors, 2 variants in the Indonesia. The Vario 160 is powered by a 156.9 cc engine, and has a Variable Speed gearbox. The Honda Vario 160 has a seating height of 778 mm and kerb weight of 117 kg. The Vario 160 comes with Disc front brakes and Disc rear brakes along with ABS. Vario 160 top competitors are Freego Connected, Vario 125, PCX160 and Scoopy.",
                displacement = 157,
                maximumPower = 15.1,
                category = "Scooter",
                oilTankCapacity = 5.5,
                price = 29410000.0,
                isFavorite = false,
            ),
        )
    }
}