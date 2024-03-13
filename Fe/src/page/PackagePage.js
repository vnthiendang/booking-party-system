import React, { useEffect, useState } from "react";
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
import {
  discountImg,
  bgFullbox,
  pizzaImg,
  Step1,
  Step2,
  Step3,
  Step4,
} from "../asset";
import { useNavigate } from "react-router-dom";
import { ROUTER } from "../util";
import { ServiceApi } from "../api";
import { listPkImg, listSvImg } from "../asset/listpkImg";

const PackagePage = () => {
  // init state
  const navigate = useNavigate();
  const [PackageList, setPackageList] = useState([]);

  const [stepList, setStepList] = useState(
    Array.from({ length: 4 }, (value, index) => index + 1)
  );
  // hanle click funtion

  const handleClickBook = (id, index) =>
    navigate(`/event/${id}?index=${index}`);
  const getAllPackaged = async () => {
    try {
      const res = await ServiceApi.getListpackageByCustomer();
      setPackageList(res);
    } catch (error) {
      alert(error);
    }
  };

  useEffect(() => {
    getAllPackaged();
  }, []);

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
              <CardPackage
                key={index}
                index={index}
                handleClickBook={handleClickBook}
                item={item}
              />
            ))}
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
                <CardServicePackage key={index} index={index} />
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
  item,
  column = 4,
  index,
  PackageCardDefault = true,
  handleClickBook,
}) => {
  /**
       * capacity 
description
packageName
id
price

services :[2, 3]
venue :"ThuDuc"
       */
  return (
    <Grid item xs={column}>
      <Card sx={{ maxWidth: "100%", background: "black", color: "white" }}>
        <CardMedia
          sx={{ height: 189, background: "black" }}
          image={listPkImg[index]}
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
              {item?.packageName}
            </Typography>
          </div>
          {PackageCardDefault ? (
            <>
              <p className="price">
                ${item?.price?.toLocaleString()}
                <br />
                <span className="details">
                  + GST for{item?.capacity} participants
                </span>
              </p>
              <div>
                <ul>
                  <li>{item?.description}</li>
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
                  onClick={() => handleClickBook(item.id, index)}
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
  const checkrenderStep = (index) => {
    switch (index) {
      case 1:
        return "Choose time of package";
      case 2:
        return "Add more service if you want";
      case 3:
        return "Login for confirm booking";
      case 4:
        return "Deposit and confirm booking";

      default:
        break;
    }
  };
  const listImg = [Step1, Step2, Step3, Step4];
  return (
    <Grid item xs={4}>
      <Card sx={{ maxWidth: 345, background: "#240044", color: "white" }}>
        <CardMedia
          sx={{ height: 189, background: "black", objectFit: "contain" }}
          image={listImg[index]}
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
            {checkrenderStep(index + 1)}
          </Typography>
        </CardContent>
      </Card>
    </Grid>
  );
};
const CardServicePackage = ({ item, index }) => {
  const checkrender = (index) => {
    switch (index) {
      case 0:
        return "Khoai tây chiên";
        break;
      case 1:
        return "Bánh mì";
        break;
      case 2:
        return "Cari gà";
        break;
      case 3:
        return "Tiramisu";
        break;
      case 4:
        return "Aquafina";
        break;

      default:
        break;
    }
  };
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
        {/* <CardMedia
          sx={{
            height: 203,
            background: "black",
            borderRadius: "30px",
          }}
          image={listSvImg[index]}
          title="green iguana"
        /> */}
        <img
          src={listSvImg[index]}
          style={{
            height: 203,
            background: "black",
            borderRadius: "30px",
          }}
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
            {checkrender(index)}
          </Typography>
        </CardContent>
      </Card>
    </Grid>
  );
};
