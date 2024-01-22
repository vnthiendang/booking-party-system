import React, { useState } from "react";
import LayoutWeb from "../layout/LayoutWeb";
import {
  Box,
  Button,
  Card,
  CardContent,
  CardMedia,
  Container,
  Grid,
  Stack,
  Typography,
} from "@mui/material";
import "./../style/style.css";
import { discountImg, bgFullbox, pizzaImg } from "../asset";
import { useNavigate } from "react-router-dom";
import { ROUTER } from "../util";

const PackagePage = () => {
  // init state
  const navigate = useNavigate();
  const [PackageList, setPackageList] = useState(
    Array.from({ length: 4 }, (value, index) => index + 1)
  );

  const [stepList, setStepList] = useState(
    Array.from({ length: 4 }, (value, index) => index + 1)
  );
  // hanle click funtion

  const handleClickBook = () => navigate(ROUTER.EVENT);

  return (
    <LayoutWeb>
      {/* step to booking package */}
      <Box
        sx={{
          background: "#000",
          backgroundImage: `url(${bgFullbox})`,
          backgroundPosition: "center",
          backgroundRepeat: "no-repeat",
          backgroundSize: "cover",
          paddingBottom: "20px",
        }}
      >
        <HeaderTextPackage text={"STEPS TO BOOK PACKAGE"} />

        <Container maxWidth="lg">
          <Grid container spacing={2} columns={16}>
            {stepList?.map((item, index) => (
              <CardStepBooking key={index} index={index} />
            ))}
          </Grid>
        </Container>
      </Box>
      {/* List package */}
      <Box
        sx={{
          background: "#292929",
          paddingBottom: "20px",
        }}
      >
        <HeaderTextPackage text={"PARTY PACKAGES"} />
        <Container maxWidth="lg">
          <Grid container spacing={2} columns={16}>
            {PackageList?.map((item, index) => (
              <CardPackage key={index} handleClickBook={handleClickBook} />
            ))}
          </Grid>
        </Container>
      </Box>
      {/* PRIVATE FACILITY RENTAL PACKAGES */}
      <Box
        sx={{
          background: "#292929",
          paddingBottom: "20px",
          marginTop: "40px",
        }}
      >
        <HeaderTextPackage text={"PRIVATE FACILITY RENTAL PACKAGES"} />
        <Container maxWidth="lg">
          <Grid container spacing={2} columns={16}>
            {Array.from({ length: 2 }, (value, index) => index + 1)?.map(
              (item, index) => (
                <CardPackage
                  key={index}
                  column={8}
                  PackageCardDefault={false}
                />
              )
            )}
          </Grid>
        </Container>
      </Box>
      {/* Service Package */}
      <Box
        sx={{
          paddingBottom: "20px",
          marginTop: "40px",
        }}
      >
        <HeaderTextPackage text={"SERVICE PACKAGES"} />
        <Container maxWidth="lg">
          <Grid container spacing={2} columns={16} justifyContent={"center"}>
            {Array.from({ length: 5 }, (value, index) => index + 1)?.map(
              (item, index) => (
                <CardServicePackage key={index} />
              )
            )}
          </Grid>
        </Container>
      </Box>
      {/* Contact */}
      <Container maxWidth="lg">
        <Box
          sx={{
            border: "10px solid #240044 ",
            marginTop: "40px",
            marginBottom: "40px",
            padding: 5,
          }}
        >
          <div
            className="header_cart"
            style={{ background: "#ff5100", color: "#fff" }}
          >
            <Typography
              gutterBottom
              variant="h2"
              component="div"
              sx={{
                textAlign: "center",
                textTransform: "uppercase",
                fontWeight: "bold",
              }}
            >
              BOOK YOUR PACKAGE
            </Typography>
          </div>
          <h3 style={{ color: "#fff", textAlign: "center", marginBottom: 20 }}>
            To book your child’s birthday party and make the event special,
            please click the link above.
          </h3>
          <h4
            style={{
              color: "#fff",
              textAlign: "center",
              marginBottom: 20,
              fontWeight: "bold",
            }}
          >
            To speak to our group coordinator, please contact via email or call:
          </h4>
          <Stack direction={"row"} justifyContent={"center"}>
            <Button
              variant="contained"
              sx={{
                fontSize: 28,
                background: "red",
                fontWeight: "bold",
              }}
            >
              example@gmail.com
            </Button>
            <Button
              variant="contained"
              sx={{
                marginLeft: 2,
                fontSize: 28,
                background: "red",
                fontWeight: "bold",
              }}
            >
              091-xxx-xxx
            </Button>
          </Stack>
        </Box>
      </Container>
    </LayoutWeb>
  );
};

export default PackagePage;

const HeaderTextPackage = ({ text }) => {
  return (
    <h1
      style={{
        textAlign: "center",
        margin: "0",
        color: "white",
        fontSize: "60px",
        fontWeight: "bold",
        paddingTop: "20px",
        paddingBottom: "60px",
      }}
    >
      {text}
    </h1>
  );
};

const CardPackage = ({
  itemm,
  column = 4,
  PackageCardDefault = true,
  handleClickBook,
}) => {
  return (
    <Grid item xs={column}>
      <Card sx={{ maxWidth: "100%", background: "black", color: "white" }}>
        <CardMedia
          sx={{ height: 189, background: "black" }}
          image="https://picsum.photos/id/237/200/300"
          title="green iguana"
        />
        <CardContent>
          <div className="header_cart">
            <Typography
              gutterBottom
              variant="h4"
              component="div"
              sx={{ textAlign: "center", textTransform: "uppercase" }}
            >
              Lizard
            </Typography>
          </div>
          {PackageCardDefault ? (
            <>
              <p className="price">
                $649.99
                <br />
                <span className="details">
                  + GST for 10 participants
                  <br />
                  $54.99 per additional participant
                </span>
              </p>
              <div>
                <ul>
                  <li>Includes 10 kids admissions to Leisure Lagoon</li>
                  <li>Includes up to 20 adult admissions to Leisure Lagoon</li>
                  <li>Private party space for 90 minutes</li>
                  <li>Unlimited Play time before &amp; after the party time</li>
                  <li>
                    1000 E-tickets for the birthday child to redeem Prizes
                  </li>
                  <li>40 arcade credits for each child</li>
                  <li>1 bumper car ride for each child</li>
                  <li>1 Virtual box ride for each child</li>
                  <li>1 On-the-fly ropes course for each child</li>
                  <li>1 Grip socks for each child</li>
                  <li>Buddy shirt for the birthday child</li>
                  <li>Goodie bag for each child</li>
                  <li>Plates, cups, cutlery, napkins &amp; table cloth</li>
                </ul>
              </div>
              <div>
                <img
                  src={discountImg}
                  alt="discounr"
                  width={"100%"}
                  height={114}
                  style={{
                    objectFit: "contain",
                  }}
                />
              </div>
              <Stack direction={"row"} justifyContent={"center"}>
                <Button
                  variant="contained"
                  sx={{
                    background: "red",
                    height: 57,
                  }}
                  onClick={handleClickBook}
                >
                  Book now
                </Button>
              </Stack>
            </>
          ) : (
            <>
              <p className="price">
                Contact us
                <br />
                for pricing at example@gmail.com
              </p>
              <div>
                <p>
                  If you only wants to enjoy our gigantic indoor play space and
                  Inflatable park, this is the perfect option for your crew of
                  upto 250 people. Package includes:
                </p>
                <ul>
                  <li style={{ listStyle: "none" }}>
                    <ul>
                      <li style={{ listStyle: "none" }}>
                        <ul>
                          <li>Leisure Lagoon Admission for each child</li>
                          <li>Private facility time for 180 minutes</li>
                          <li>100 arcade credits for each child</li>
                          <li>1 Grip socks for each child</li>
                          <li>
                            plates, cups, cutlery, napkins &amp; table cloth
                          </li>
                        </ul>
                      </li>
                    </ul>
                  </li>
                </ul>
              </div>
            </>
          )}
        </CardContent>
      </Card>
    </Grid>
  );
};

const CardStepBooking = ({ item, index }) => {
  return (
    <Grid item xs={4}>
      <Card sx={{ maxWidth: 345, background: "#240044", color: "white" }}>
        <CardMedia
          sx={{ height: 189, background: "black" }}
          image="https://picsum.photos/id/237/200/300"
          title="green iguana"
        />
        <CardContent>
          <Typography
            gutterBottom
            variant="h4"
            component="div"
            sx={{ textAlign: "center", textTransform: "uppercase" }}
          >
            Step {index + 1}
          </Typography>

          <Typography
            gutterBottom
            variant="p"
            component="div"
            sx={{
              color: "#999",
              fontSize: "18px",
              marginBottom: "40px",
            }}
          >
            The Big Box offers options to book your party during operational
            hours or as a private event. We have predefined party bundles for
            you to choose from. Select what meets best for your needs. Consider
            upgrading to infinite pass on a discounted price for unlimited fun
            extravaganza!
          </Typography>

          <Typography
            gutterBottom
            variant="p"
            component="div"
            sx={{
              color: "yellow",
              fontSize: "16px",
            }}
          >
            Book now!
          </Typography>
        </CardContent>
      </Card>
    </Grid>
  );
};
const CardServicePackage = ({ item }) => {
  return (
    <Grid item xs={3}>
      <Card
        sx={{
          maxWidth: "100%",
          background: "#00003e ",
          color: "white",
          borderRadius: "30px",
        }}
      >
        <CardMedia
          sx={{ height: 203, background: "black", borderRadius: "30px" }}
          image={pizzaImg}
          title="green iguana"
        />
        <CardContent>
          <Typography
            gutterBottom
            variant="h4"
            component="div"
            sx={{
              textAlign: "center",
              textTransform: "uppercase",
              fontWeight: "bold",
            }}
          >
            Food Combo
          </Typography>

          <Typography
            gutterBottom
            variant="p"
            component="div"
            sx={{
              color: "#999",
              fontSize: "18px",
              marginBottom: "40px",
            }}
          >
            3‐12″ Pizza & 2 Jugs of Pop (Choice of Cheese, Pepperoni, Hawaiian,
            Chicken, Beef or Veggie)
          </Typography>
          <p className="price">
            $649.99
            <br />
            <span className="details">+ GST</span>
          </p>
        </CardContent>
      </Card>
    </Grid>
  );
};
